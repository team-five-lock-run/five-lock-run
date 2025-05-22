package team.project.fiverockrun.domain.waitLine.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WaitLinesRequestDto {
    private Long date;
    private Long time;
    private Long departureId;
    private Long arrivalId;
    private Integer people;
}
