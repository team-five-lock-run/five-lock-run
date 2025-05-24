package team.project.fiverockrun.domain.reservation.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import team.project.fiverockrun.common.exception.BaseException;
import team.project.fiverockrun.domain.reservation.dto.request.ReservationRequest;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    private static final Long TRAIN_ID = 1L;
    private static final Long CAR_ID = 3L;
    private static final Long SEAT_ID = 3L;
    private static final Long DEPARTURE_STATION_ID = 5L;
    private static final Long ARRIVAL_STATION_ID = 5L;
    private static final LocalDateTime DEPARTURE_TIME = LocalDateTime.of(2025, 5, 25, 11, 30);
    private static final LocalDateTime ARRIVAL_TIME = LocalDateTime.of(2025, 5, 25, 12, 30);
    private static final Integer SEAT_PRICE = 34500;

    @Test
    void testConcurrentReservation() {
        final int totalReservations = 1000; // 총 예약 시도 수
        final int[] successCount = {0}; // 예약 성공 횟수
        final int[] failedCount = {0}; // 예약 실패 횟수

        IntStream.range(0, totalReservations).parallel().forEach(i -> {
            try {
                ReservationRequest request = new ReservationRequest(
                        TRAIN_ID,
                        CAR_ID,
                        SEAT_ID,
                        DEPARTURE_STATION_ID,
                        ARRIVAL_STATION_ID,
                        DEPARTURE_TIME,
                        ARRIVAL_TIME,
                        SEAT_PRICE
                );
                reservationService.reserveSeatWithLock(request, (long) i);

                synchronized (successCount) {
                    successCount[0]++;
                }
            } catch (BaseException e) {
                synchronized (failedCount) {
                    failedCount[0]++;
                }
            }
        });

        // 예약 성공 횟수 1명, 실패 횟수 999명 확인
        assertEquals(1, successCount[0]);
        assertEquals(999, failedCount[0]);

        System.out.println("ReservationServiceTest 예약 성공: " + successCount[0]);
        System.out.println("ReservationServiceTest 예약 실패: " + failedCount[0]);
    }
}
