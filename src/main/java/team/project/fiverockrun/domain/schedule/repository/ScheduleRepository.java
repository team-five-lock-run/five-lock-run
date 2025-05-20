package team.project.fiverockrun.domain.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.project.fiverockrun.domain.route.entity.Route;
import team.project.fiverockrun.domain.schedule.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
