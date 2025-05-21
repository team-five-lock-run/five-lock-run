package team.project.fiverockrun.domain.train.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import team.project.fiverockrun.domain.train.entity.TrainCar;

import java.util.List;

public interface TrainCarRepository extends JpaRepository<TrainCar, Long> {
    List<TrainCar> findByTrainId(Long trainId);
}
