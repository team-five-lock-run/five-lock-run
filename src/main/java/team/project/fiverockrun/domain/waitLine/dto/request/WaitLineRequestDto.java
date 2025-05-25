package team.project.fiverockrun.domain.waitLine.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WaitLineRequestDto {
    @NotNull(message = "출발 날짜는 필수 입력입니다.")
    private final String departureDate;
    @NotNull(message = "출발 시간은 필수 입력입니다.")
    private final String departureTime;
    @NotNull(message = "도착 날짜, 시간은 필수 입력입니다.")
    private final String arrivalDateTime;
    @NotNull(message = "출발역은 필수 입력입니다.")
    private final Long departureStation;
    @NotNull(message = "도착역은 필수 입력입니다.")
    private final Long arrivalStation;
    @NotNull(message = "인원수는 필수 입력입니다.")
    private final int passengerCount;
}
