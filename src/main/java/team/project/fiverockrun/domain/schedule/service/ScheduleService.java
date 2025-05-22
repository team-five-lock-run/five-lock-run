package team.project.fiverockrun.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.project.fiverockrun.common.exception.BaseException;
import team.project.fiverockrun.domain.route.entity.Route;
import team.project.fiverockrun.domain.route.repository.RouteRepository;
import team.project.fiverockrun.domain.schedule.dto.response.ScheduleResponse;
import team.project.fiverockrun.domain.schedule.entity.Schedule;
import team.project.fiverockrun.domain.schedule.excetion.ScheduleError;
import team.project.fiverockrun.domain.schedule.repository.ScheduleRepository;
import team.project.fiverockrun.domain.train.entity.Train;
import team.project.fiverockrun.domain.train.repository.TrainRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final TrainRepository trainRepository;
    private final RouteRepository routeRepository;
    private final ScheduleRepository scheduleRepository;

    // 열차 스케줄 생성 - 특정 날짜에 대해 모든 역차가 1시간 간격으로 스케줄을 생성
    @Transactional
    public List<ScheduleResponse> generateSchedule(Long trainId, LocalDate date, LocalTime startTime) {

        Train train = trainRepository.findById(trainId)
                .orElseThrow(() -> new BaseException(ScheduleError.CANNOT_FIND_TRAIN));


        List<Route> routes = routeRepository.findByTrainIdOrderByOrderAsc(trainId);

        if (routes.isEmpty()) {
            throw new BaseException(ScheduleError.ROUTE_NOT_FOUND_TRAIN);
        }

        List<Schedule> createSchedules = new ArrayList<>();

        LocalDateTime baseDateTime = LocalDateTime.of(date, startTime);

        for (int i = 0; i < routes.size(); i++) {

            LocalDateTime currentDateTime = baseDateTime.plusHours(i);

            Schedule schedule = Schedule.builder()
                    .train(train)
                    .station(routes.get(i).getStation())
                    .departureDate(currentDateTime.toLocalDate())
                    .departureTime(currentDateTime.toLocalTime())
                    .build();

            scheduleRepository.save(schedule);
            createSchedules.add(schedule);

        }

        return createSchedules.stream()
                .map(schedule -> new ScheduleResponse(
                        schedule.getId(),
                        schedule.getTrain().getId(),
                        schedule.getTrain().getName(),
                        schedule.getStation().getId(),
                        schedule.getStation().getName(),
                        schedule.getDepartureDate(),
                        schedule.getDepartureTime()
                ))
                .collect(Collectors.toList());


    }

}
