package team.project.fiverockrun.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import team.project.fiverockrun.common.exception.BaseException;
import team.project.fiverockrun.domain.reservation.dto.request.ReservationRequest;
import team.project.fiverockrun.domain.reservation.dto.response.ReservationResponse;
import team.project.fiverockrun.domain.reservation.exception.ReservationError;
import team.project.fiverockrun.domain.reservation.model.Reservation;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedissonClient redissonClient;

    private static final String RESERVATION_PREFIX = "reservation:";
    private static final String USER_PREFIX = "user-reservation:";
    private static final String LOCK_SEAT_PREFIX = "lock:seat:";

    /**
     * 좌석에 대한 분산 락을 적용하여 예약 처리
     * 중복 예약이 발생하지 않도록 Redis 에 예약 정보 저장
     *
     * @param request 예약 요청 정보
     * @param userId  로그인한 사용자 ID
     * @return 저장된 예약 정보
     * @throws BaseException 예약 중 락 획득 실패, 중복 예약, 인터럽트 발생 시
     */
    public ReservationResponse reserveSeatWithLock(ReservationRequest request, Long userId) {
        // 좌석 단위로 분산 락 적용
        String lockKey = generateKey(
                LOCK_SEAT_PREFIX,
                request.getTrainId(),
                request.getCarId(),
                request.getSeatId(),
                request.getDepartureStationId(),
                request.getArrivalStationId(),
                request.getDepartureTime(),
                request.getArrivalTime()
        );

        RLock lock = redissonClient.getLock(lockKey);

        try {
            // 3초 안에 락 획득 시도, 성공 시 10초간 유지
            boolean isLocked = lock.tryLock(3, 10, TimeUnit.SECONDS);
            // 락을 못 잡으면 예외: 다른 사용자가 락 선점 중
            if (!isLocked) {
                log.error("Lock 획득 실패: " + lockKey);
                throw new BaseException(ReservationError.LOCK_FAIL);
            } else {
                log.info("Lock 획득 성공: " + lockKey);
            }

            try {
                String reservationKey = generateKey(
                        RESERVATION_PREFIX,
                        request.getTrainId(),
                        request.getCarId(),
                        request.getSeatId(),
                        request.getDepartureStationId(),
                        request.getArrivalStationId(),
                        request.getDepartureTime(),
                        request.getArrivalTime()
                );

                // 중복 예약 체크
                if (redisTemplate.hasKey(reservationKey)) {
                    log.error("중복 예약 시도: " + userId);
                    throw new BaseException(ReservationError.ALREADY_RESERVED);
                }

                // 예약 객체 생성 및 저장
                Reservation reservation = Reservation.of(request, userId);
                saveReservation(reservation);

                // 예약 저장 후 예약 정보 반환
                return ReservationResponse.from(reservationKey, reservation);

            } finally {
                lock.unlock();
                log.info("Lock 해제 성공: " + lockKey);
            }

        } catch (InterruptedException e) {
            // 락 시도 중 쓰레드가 끊겼을 때 처리
            Thread.currentThread().interrupt();
            throw new BaseException(ReservationError.INTERRUPTED);
        }
    }

    /**
     * 예약 정보를 Redis 에 저장
     *
     * @param reservation 저장할 예약 객체
     */
    public void saveReservation(Reservation reservation) {
        String reservationKey = generateKey(
                RESERVATION_PREFIX,
                reservation.getTrainId(),
                reservation.getCarId(),
                reservation.getSeatId(),
                reservation.getDepartureStationId(),
                reservation.getArrivalStationId(),
                reservation.getDepartureTime(),
                reservation.getArrivalTime()
        );

        String userKey = generateKey(
                USER_PREFIX,
                reservation.getUserId(),
                reservation.getTrainId(),
                reservation.getCarId(),
                reservation.getSeatId(),
                reservation.getDepartureStationId(),
                reservation.getArrivalStationId(),
                reservation.getDepartureTime(),
                reservation.getArrivalTime()
        );

        Duration ttl = (reservation.getReservedAt() != null && reservation.getExpireAt() != null) ?
                Duration.between(reservation.getReservedAt(), reservation.getExpireAt()) : Duration.ofMinutes(10);

        log.info("예약 등록 키: " + reservationKey);
        redisTemplate.opsForValue().set(reservationKey, reservation, ttl);

        log.info("사용자 키: " + userKey);
        redisTemplate.opsForValue().set(userKey, reservation, ttl);
    }

    /**
     * 특정 사용자의 예약 목록 조회
     *
     * @param userId 로그인한 사용자 ID
     * @return 사용자의 예약 목록
     */
    public List<ReservationResponse> getReservationsByUser(Long userId) {
        String pattern = USER_PREFIX + userId + ":*";
        return getReservations(pattern);
    }

    /**
     * 특정 좌석의 예약 목록 조회
     *
     * @param trainId 기차 ID
     * @param carId   차량 ID
     * @param seatId  좌석 ID
     * @return 좌석의 예약 목록
     */
    public List<ReservationResponse> getReservationsBySeat(Long trainId, Long carId, Long seatId) {
        String pattern = RESERVATION_PREFIX + trainId + ":" + carId + ":" + seatId + ":*";
        return getReservations(pattern);
    }

    /**
     * 특정 차량의 예약 ID 목록 조회
     * - 차량 검색 시 사용되며, 특정 열차 차량의 예약된 좌석 ID를 반환합니다.
     *
     * @param trainId 기차 ID
     * @param carId 차량 ID
     * @return 현재 예약 중인 좌석 ID 목록
     */
    public List<Long> getSeatsByTrainCar(Long trainId, Long carId) {
        String pattern = RESERVATION_PREFIX + trainId + ":" + carId + ":*";
        List<Long> reservedSeats = new ArrayList<>();

        ScanOptions options = ScanOptions.scanOptions()
                .match(pattern)
                .count(50)
                .build();

        Cursor<byte[]> cursor = redisTemplate.getConnectionFactory().getConnection().scan(options);

        try {
            while (cursor.hasNext()) {
                byte[] key = cursor.next();
                String keyString = new String(key);

                Object value = redisTemplate.opsForValue().get(keyString);

                if (value instanceof Reservation reservation) {
                    reservedSeats.add(reservation.getSeatId());
                }

            }
        } finally {
            cursor.close();
        }

        return reservedSeats;
    }

    /**
     * 특정 좌석의 예약 단건 조회
     *
     * @param trainId            기차 ID
     * @param carId              차량 ID
     * @param seatId             좌석 ID
     * @param departureStationId 출발역 ID
     * @param arrivalStationId   도착역 ID
     * @param departureTime      출발 일시
     * @param arrivalTime        도착 일시
     * @return 해당 좌석의 예약 정보
     */
    public ReservationResponse getReservation(
            Long trainId,
            Long carId,
            Long seatId,
            Long departureStationId,
            Long arrivalStationId,
            LocalDateTime departureTime,
            LocalDateTime arrivalTime
    ) {
        String key = generateKey(
                RESERVATION_PREFIX,
                trainId,
                carId,
                seatId,
                departureStationId,
                arrivalStationId,
                departureTime,
                arrivalTime
        );

        Object result = redisTemplate.opsForValue().get(key);

        if (result == null) {
            throw new BaseException(ReservationError.RESERVATION_NOT_FOUND);
        }

        Reservation reservation = (Reservation) result;
        return ReservationResponse.from(key, reservation);
    }

    /**
     * 예약 정보 삭제
     *
     * @param userId             사용자 ID
     * @param trainId            기차 ID
     * @param carId              차량 ID
     * @param seatId             좌석 ID
     * @param departureStationId 출발역 ID
     * @param arrivalStationId   도착역 ID
     * @param departureTime      출발 일시
     * @param arrivalTime        도착 일시
     */
    public void deleteReservation(
            Long userId,
            Long trainId,
            Long carId,
            Long seatId,
            Long departureStationId,
            Long arrivalStationId,
            LocalDateTime departureTime,
            LocalDateTime arrivalTime
    ) {
        String reservationKey = generateKey(
                RESERVATION_PREFIX,
                trainId,
                carId,
                seatId,
                departureStationId,
                arrivalStationId,
                departureTime,
                arrivalTime
        );

        String userKey = generateKey(
                USER_PREFIX,
                userId,
                trainId,
                carId,
                seatId,
                departureStationId,
                arrivalStationId,
                departureTime,
                arrivalTime
        );

        redisTemplate.delete(reservationKey);
        redisTemplate.delete(userKey);
    }

    /**
     * Redis 키 생성
     *
     * @param prefix 키의 접두사 (예약, 사용자, 락)
     * @param params 키를 구성하는 매개변수
     * @return 생성된 Redis 키
     */
    private String generateKey(String prefix, Object... params) {
        return prefix + String.join(":", Arrays.stream(params)
                .map(Objects::toString)
                .collect(Collectors.toList()));
    }

    /**
     * 패턴 기반 예약 목록 조회
     *
     * @param pattern 조회할 키의 패턴
     * @return 조회된 예약 정보 목록
     */
    private List<ReservationResponse> getReservations(String pattern) {
        List<ReservationResponse> reservationResponses = new ArrayList<>();

        ScanOptions options = ScanOptions.scanOptions()
                .match(pattern)
                .count(100)
                .build();

        log.info("예약 정보 조회 중...");
        Cursor<byte[]> cursor = redisTemplate.getConnectionFactory().getConnection().scan(options);

        try {
            while (cursor.hasNext()) {
                byte[] key = cursor.next();
                String keyString = new String(key);
                Object value = redisTemplate.opsForValue().get(keyString);

                if (value instanceof Reservation reservation) {
                    log.info("예약 정보 조회 성공: " + keyString);
                    ReservationResponse reservationResponse = ReservationResponse.from(keyString, reservation);
                    reservationResponses.add(reservationResponse);
                } else {
                    log.info("예약 정보 조회 값 null: " + keyString);
                }
            }
        } finally {
            cursor.close();
        }

        return reservationResponses;
    }
}
