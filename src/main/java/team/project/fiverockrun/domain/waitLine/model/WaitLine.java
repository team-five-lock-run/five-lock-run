package team.project.fiverockrun.domain.waitLine.model;

import lombok.*;
import team.project.fiverockrun.domain.waitLine.dto.request.WaitLinesRequestDto;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
public class WaitLine implements Serializable {
    private Long userId;
    private Long date;
    private Long time;
    private Long departureId;
    private Long arrivalId;
    private Integer people;

    public WaitLine(Long userId, Long date, Long time, Long departureId, Long arrivalId, Integer people) {
        this.userId = userId;
        this.date = date;
        this.time = time;
        this.departureId = departureId;
        this.arrivalId = arrivalId;
        this.people = people;
    }
}
