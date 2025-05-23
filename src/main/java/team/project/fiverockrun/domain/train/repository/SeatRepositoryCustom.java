package team.project.fiverockrun.domain.train.repository;

import java.time.LocalDate;
import java.util.List;

public interface SeatRepositoryCustom {

    List<String> findAvailableSeats(Long id, Long departureStation, Long arrivalStation, LocalDate departureDate);

}
