package team.project.fiverockrun.domain.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.project.fiverockrun.domain.reservation.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
