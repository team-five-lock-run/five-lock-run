package team.project.fiverockrun.domain.station.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.project.fiverockrun.domain.station.dto.request.StationRequest;
import team.project.fiverockrun.domain.station.dto.response.StationResponse;
import team.project.fiverockrun.domain.station.entity.Station;
import team.project.fiverockrun.domain.station.repository.StationRepository;

import java.util.IllformedLocaleException;
import java.util.List;

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

    @Transactional(readOnly = true)
    public List<StationResponse> getAllStation() {
        return stationRepository.findAllStation();
    }

    @Transactional
    public void disableStation(Long id) {
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new IllformedLocaleException("해당 역이 존재하지 않습니다."));
        station.setActive(false);
        stationRepository.save(station);
    }
}
