package team.project.fiverockrun.domain.ticket.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TicketResponse {
    private String ticketName;
    private String trainName;
    private Integer carNumber;
    private String seatNumber;
    private Integer price;
    private LocalDateTime paidAt;
}

