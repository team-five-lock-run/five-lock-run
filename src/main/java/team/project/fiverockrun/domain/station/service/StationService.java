package team.project.fiverockrun.domain.station.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.project.fiverockrun.domain.station.dto.request.StationRequest;
import team.project.fiverockrun.domain.station.dto.response.StationResponse;
import team.project.fiverockrun.domain.station.entity.Station;
import team.project.fiverockrun.domain.station.repository.StationRepository;

@Service
@RequiredArgsConstructor
public class StationService {

    private final StationRepository stationRepository;

    @Transactional
    public StationResponse createStation(StationRequest stationRequest) {

        Station newStation = new Station(
                stationRequest.getName(),
                stationRequest.getRegion()
        );

        Station saveStation = stationRepository.save(newStation);

        return new StationResponse(saveStation);

    }
}
