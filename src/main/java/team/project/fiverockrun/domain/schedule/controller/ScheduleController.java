package team.project.fiverockrun.domain.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.project.fiverockrun.domain.schedule.dto.response.ScheduleResponse;
import team.project.fiverockrun.domain.schedule.repository.ScheduleRepository;
import team.project.fiverockrun.domain.schedule.service.ScheduleService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 스케줄 자동 생성
    // /schedules/generate?date=2025-06-01
    @PostMapping
    public ResponseEntity<List<ScheduleResponse>> generateSchedules(
            @RequestParam("trainId") Long trainId,
            @RequestParam("date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate date,
            @RequestParam("startTime")
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime
            ) {
        List<ScheduleResponse> generated = scheduleService.generateSchedule(trainId, date, startTime);
        return ResponseEntity.ok(generated);
    }
}
