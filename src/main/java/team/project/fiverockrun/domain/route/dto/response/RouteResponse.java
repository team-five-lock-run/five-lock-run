package team.project.fiverockrun.domain.route.dto.response;

import lombok.Getter;

@Getter
public class RouteResponse {

    private final String station;
    private final Long train;
    private final Long oreder;
    private final Boolean isEnabled;

    public RouteResponse(String station, Long train, Long oreder, Boolean isEnabled) {
        this.station = station;
        this.train = train;
        this.oreder = oreder;
        this.isEnabled = isEnabled;
    }

}
