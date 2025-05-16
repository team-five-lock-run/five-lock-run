package team.project.fiverockrun.domain.seat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.project.fiverockrun.domain.seat.entity.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
