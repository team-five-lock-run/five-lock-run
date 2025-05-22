package team.project.fiverockrun.domain.reservation.dto.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationRequest {

    private Long trainId;
    private Long carId;
    private Long seatId;
    private String seatNumber;
    private Long departureStationId;
    private Long arrivalStationId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Integer seatPrice;

}
