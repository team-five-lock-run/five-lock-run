package team.project.fiverockrun.domain.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.project.fiverockrun.domain.route.entity.Route;
import team.project.fiverockrun.domain.schedule.entity.Schedule;
import team.project.fiverockrun.domain.station.entity.Station;
import team.project.fiverockrun.domain.train.entity.Train;

import java.time.LocalDate;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findByTrainAndStationAndDepartureDate(Train train, Station departureStation, LocalDate departureDate);
}
