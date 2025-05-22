package team.project.fiverockrun.domain.route.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RouteRequest {

    private Long trainId;
    private Long stationId;
    private Long order;

}
