package team.project.fiverockrun.domain.train.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.project.fiverockrun.domain.train.enums.TrainType;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TrainRequest {

    private Long trainNumber;
    private String name;
    private TrainType type;
    private int carCount;
    private int seatsCount;
    private int premiumCarCount;

}
