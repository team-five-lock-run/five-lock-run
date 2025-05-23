package team.project.fiverockrun.domain.booking.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BookingResponse {
    private Long bookingId;
    private String trainName;
    private Integer carNumber;
    private String seatNumber;
    private Integer price;
    private LocalDateTime paidAt;
    private String departureStation;
    private LocalDateTime departureTime;
    private String arrivalStation;
    private LocalDateTime arrivalTime;

}
