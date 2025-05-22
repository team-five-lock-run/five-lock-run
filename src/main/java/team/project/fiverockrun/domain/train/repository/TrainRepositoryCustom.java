package team.project.fiverockrun.domain.train.repository;

import team.project.fiverockrun.domain.station.dto.response.StationResponse;
import team.project.fiverockrun.domain.train.dto.request.TrainRequest;
import team.project.fiverockrun.domain.train.dto.response.TrainSerchResponse;

import java.util.List;

public interface TrainRepositoryCustom {

    List<TrainSerchResponse> searchTrain(TrainRequest request);

}
