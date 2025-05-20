package team.project.fiverockrun.domain.train.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.project.fiverockrun.domain.train.entity.TrainCar;

public interface TrainCarRepository extends JpaRepository<TrainCar, Long> {
}
