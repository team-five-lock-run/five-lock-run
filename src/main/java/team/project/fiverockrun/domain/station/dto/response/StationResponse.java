package team.project.fiverockrun.domain.station.dto.response;

import lombok.Getter;
import team.project.fiverockrun.domain.station.entity.Station;

@Getter
public class StationResponse {

    private final String name;
    private final String region;

    public StationResponse(String region, String name) {
        this.region = region;
        this.name = name;
    }

    public StationResponse(Station station) {
        this.region = station.getRegion();
        this.name = station.getName();
    }


}
