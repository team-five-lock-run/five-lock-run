package team.project.fiverockrun.domain.station.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.project.fiverockrun.domain.station.entity.Station;

public interface StationRepository extends JpaRepository<Station, Long>, StationRepositoryCustom {
}
