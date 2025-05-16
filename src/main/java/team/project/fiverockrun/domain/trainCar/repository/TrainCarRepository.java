package team.project.fiverockrun.domain.trainCar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.project.fiverockrun.domain.trainCar.entity.TrainCar;

public interface TrainCarRepository extends JpaRepository<TrainCar, Long> {
}
