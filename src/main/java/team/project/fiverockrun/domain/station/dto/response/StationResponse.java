package team.project.fiverockrun.domain.station.dto.response;

import lombok.Getter;
import team.project.fiverockrun.domain.station.entity.Station;

@Getter
public class StationResponse {

    private final String name;
    private final String region;

    public StationResponse(String name, String region) {
        this.name = name;
        this.region = region;
    }

    public StationResponse(Station station) {
        this.name = station.getName();
        this.region = station.getRegion();
    }


}
