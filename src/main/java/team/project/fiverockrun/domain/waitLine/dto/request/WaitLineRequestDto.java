package team.project.fiverockrun.domain.waitLine.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class WaitLineRequestDto {
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalDateTime arrivalDateTime;
    private Long departureStation;
    private Long arrivalStation;
    private int passengerCount;
}
