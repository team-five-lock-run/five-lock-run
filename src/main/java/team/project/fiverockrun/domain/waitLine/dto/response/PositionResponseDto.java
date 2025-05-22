package team.project.fiverockrun.domain.waitLine.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PositionResponseDto {
    private final Long postion;
    private final Long total;
}
