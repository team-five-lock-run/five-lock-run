package team.project.fiverockrun.domain.train.dto.response;

import lombok.Getter;

@Getter
public class UpdateTrainResponse {

    private final int carCount;
    private final int seatsCount;
    private final int premiumCarCount;

    public UpdateTrainResponse(int carCount, int seatsCount, int premiumCarCount) {
        this.carCount = carCount;
        this.seatsCount = seatsCount;
        this.premiumCarCount = premiumCarCount;
    }

}
