package team.project.fiverockrun.domain.station.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StationRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String region;

}
