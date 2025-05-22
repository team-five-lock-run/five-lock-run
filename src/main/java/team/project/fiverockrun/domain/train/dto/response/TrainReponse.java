package team.project.fiverockrun.domain.train.dto.response;

import lombok.Getter;
import team.project.fiverockrun.domain.train.enums.TrainType;

@Getter
public class TrainReponse {

    private final Long trainNumber;
    private final String name;
    private final TrainType type;
    private final int carCount;
    private final int seatsCount;
    private final int premiumCarCount;

    public TrainReponse(Long trainNumber, String name, TrainType type, int carCount, int seatsCount, int premiumCarCount) {
        this.trainNumber = trainNumber;
        this.name = name;
        this.type = type;
        this.carCount = carCount;
        this.seatsCount = seatsCount;
        this.premiumCarCount = premiumCarCount;
    }

}
