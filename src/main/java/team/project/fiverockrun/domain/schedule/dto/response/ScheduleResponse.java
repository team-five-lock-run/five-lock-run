package team.project.fiverockrun.domain.schedule.dto.response;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ScheduleResponse {

    private Long scheduleId;          // 스케줄 ID
    private Long trainId;            // 열차 ID
    private String trainName;        // 열차 이름 (optional, 있으면 유용)
    private Long stationId;          // 현재 정차역 ID
    private String stationName;      // 현재 정차역 이름
    private LocalDate departureDate; // 출발 날짜
    private LocalTime departureTime; // 출발 시각

    public ScheduleResponse(Long scheduleId, Long trainId, String trainName, Long stationId, String stationName, LocalDate departureDate, LocalTime departureTime) {
        this.scheduleId = scheduleId;
        this.trainId = trainId;
        this.trainName = trainName;
        this.stationId = stationId;
        this.stationName = stationName;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
    }
}
