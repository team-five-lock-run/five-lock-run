package team.project.fiverockrun.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import team.project.fiverockrun.domain.reservation.model.Reservation;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void saveReservation(Reservation reservation) {
        String key = generateKey(reservation.getSeatId());

        redisTemplate.opsForValue().set(
                key,
                reservation,
                Duration.between(reservation.getReservedAt(), reservation.getExpireAt()) // TTL
        );
    }

    public Reservation getReservation(Long seatId) {
        String key = generateKey(seatId);
        return (Reservation) redisTemplate.opsForValue().get(key);
    }

    public void deleteReservation(Long seatId) {
        String key = generateKey(seatId);
        redisTemplate.delete(key);
    }

    private String generateKey(Long seatId) {
        return "reservation:" + seatId;
    }
}
