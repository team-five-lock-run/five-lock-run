package team.project.fiverockrun.domain.station.repository;

import team.project.fiverockrun.domain.station.dto.response.StationResponse;

import java.util.List;

public interface StationRepositoryCustom{

    List<StationResponse> findAllStation();

}
