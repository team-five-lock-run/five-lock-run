package team.project.fiverockrun.domain.waitLine.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class WaitLine implements Serializable {
    private Long userId;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalDateTime arrivalDateTime;
    private Long departureStation;
    private Long arrivalStation;
    private int passengerCount;

    public WaitLine(Long userId, LocalDate departureDate, LocalTime departureTime, LocalDateTime arrivalDateTime, Long departureStation, Long arrivalStation, int passengerCount) {
        this.userId = userId;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDateTime = arrivalDateTime;
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.passengerCount = passengerCount;
    }
}
