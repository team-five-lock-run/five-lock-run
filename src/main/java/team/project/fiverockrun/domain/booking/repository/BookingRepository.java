package team.project.fiverockrun.domain.booking.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import team.project.fiverockrun.domain.booking.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByUserId(Long userId);   // 사용자별 예매 목록 조회
}
