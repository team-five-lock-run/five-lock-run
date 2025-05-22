package team.project.fiverockrun.domain.train.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTrainRequest {

    private int carCount;
    private int seatsCount;
    private int premiumCarCount;

}
