package team.project.fiverockrun.domain.waitLine.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WaitLineResponseDto {
    private final boolean success;
    private final String message;
}
