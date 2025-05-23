package team.project.fiverockrun.domain.booking.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BookingRequest {

    @NotNull
    private Long trainId;
    @NotNull
    private Long trainCarId;
    @NotNull
    private Long seatId;
    @NotNull
    private Integer seatPrice;
    @NotNull
    private LocalDateTime paidAt;
    @NotNull
    private LocalDateTime departureTime;
    @NotNull
    private LocalDateTime arrivalTime;
    @NotNull
    private Long departureStationId;
    @NotNull
    private Long arrivalStationId;

}