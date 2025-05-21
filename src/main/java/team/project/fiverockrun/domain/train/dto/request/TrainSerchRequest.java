package team.project.fiverockrun.domain.train.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrainSerchRequest {

    private String departureStation;
    private String arrivalStation;
    private LocalDateTime departureDateTime;
    private int passengerCount;

}