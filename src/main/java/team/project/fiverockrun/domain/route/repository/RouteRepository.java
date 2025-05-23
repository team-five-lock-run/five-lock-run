package team.project.fiverockrun.domain.route.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.project.fiverockrun.domain.route.entity.Route;

import java.util.List;
import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route, Long> {

    List<Route> findByTrainIdOrderByOrderAsc(Long trainId);

    Optional<Route> findByTrain_IdAndStation_Id(Long trainId, Long stationId);

}
