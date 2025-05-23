package team.project.fiverockrun.domain.train.repository;

import team.project.fiverockrun.domain.station.dto.response.StationResponse;
import team.project.fiverockrun.domain.train.dto.request.TrainRequest;
import team.project.fiverockrun.domain.train.dto.request.TrainSerchRequest;
import team.project.fiverockrun.domain.train.dto.response.TrainSerchResponse;

import java.time.LocalDate;
import java.util.List;

public interface TrainRepositoryCustom {

    List<TrainSerchResponse> searchTrain(TrainSerchRequest request);

}
