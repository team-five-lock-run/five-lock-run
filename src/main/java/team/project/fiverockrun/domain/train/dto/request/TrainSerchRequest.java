package team.project.fiverockrun.domain.train.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrainSerchRequest {

    private Long departureStation;
    private Long arrivalStation;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private int passengerCount;

}