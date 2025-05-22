package team.project.fiverockrun.domain.train.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.project.fiverockrun.domain.train.entity.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
