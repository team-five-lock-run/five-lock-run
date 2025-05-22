package team.project.fiverockrun.domain.reservation.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    private Long trainId;
    private Long carId;
    private Long seatId;
    private String seatNumber;
    private Long departureStationId;
    private Long arrivalStationId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Integer seatPrice;
    private Long userId;
    private LocalDateTime reservedAt;
    private LocalDateTime expireAt;

/*
    public static Reservation of(SeatResponse dto, Long userId) {
        LocalDateTime now = LocalDateTime.now();
        return new Reservation(
                dto.getTrainId(),
                dto.getCarId(),
                dto.getSeatId(),
                dto.getSeatNumber(),
                dto.getDepartureStationId(),
                dto.getArrivalStationId(),
                dto.getDepartureTime(),
                dto.getArrivalTime(),
                dto.getSeatPrice(),
                userId,
                now,
                now.plusMinutes(10)
        );
    }
*/
}
