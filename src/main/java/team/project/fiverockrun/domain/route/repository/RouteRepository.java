package team.project.fiverockrun.domain.route.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.project.fiverockrun.domain.route.entity.Route;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {

    List<Route> findByTrainIdOrderByOrderAsc(Long trainId);

}
