package team.project.fiverockrun.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import team.project.fiverockrun.common.exception.BaseException;
import team.project.fiverockrun.domain.reservation.exception.ReservationError;
import team.project.fiverockrun.domain.reservation.model.Reservation;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void saveReservation(Reservation reservation) {
        String seatKey = generateSeatKey(reservation.getSeatId());
        String userKey = generateUserKey(reservation.getUserId(), reservation.getSeatId());
        Duration ttl = Duration.between(reservation.getReservedAt(), reservation.getExpireAt());

        redisTemplate.opsForValue().set(seatKey, reservation, ttl);
        redisTemplate.opsForValue().set(userKey, reservation, ttl);
    }

    public List<Reservation> getReservationsByUser(Long userId) {
        Set<String> keys = redisTemplate.keys("user-reservation:" + userId + ":*");
        if (keys == null || keys.isEmpty()) return Collections.emptyList();

        List<Object> values = redisTemplate.opsForValue().multiGet(keys);
        if (values == null) return Collections.emptyList();

        return values.stream()
                .filter(Objects::nonNull)
                .map(obj -> (Reservation) obj)
                .collect(Collectors.toList());
    }

    public Reservation getReservation(Long seatId) {
        String key = generateSeatKey(seatId);
        Object result = redisTemplate.opsForValue().get(key);

        if (result == null) {
            throw new BaseException(ReservationError.RESERVATION_NOT_FOUND);
        }

        return (Reservation) result;
    }

    public void deleteReservation(Long userId, Long seatId) {
        String seatKey = generateSeatKey(seatId);
        String userKey = generateUserKey(userId, seatId);

        redisTemplate.delete(seatKey);
        redisTemplate.delete(userKey);
    }

    private String generateSeatKey(Long seatId) {
        return "reservation:" + seatId;
    }

    private String generateUserKey(Long userId, Long seatId) {
        return "user-reservation:" + userId + ":" + seatId;
    }
}
