package team.project.fiverockrun.domain.waitLine.model;

import lombok.*;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class WaitLine implements Serializable {
    private Long userId;
    private String departureDate;
    private String departureTime;
    private String arrivalDateTime;
    private Long departureStation;
    private Long arrivalStation;
    private int passengerCount;

    public WaitLine(Long userId, String departureDate, String departureTime, String arrivalDateTime, Long departureStation, Long arrivalStation, int passengerCount) {
        this.userId = userId;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDateTime = arrivalDateTime;
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.passengerCount = passengerCount;
    }
}
