package team.project.fiverockrun.domain.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.project.fiverockrun.domain.booking.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
