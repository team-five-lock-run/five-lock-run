package team.project.fiverockrun.domain.train.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.project.fiverockrun.domain.train.entity.Train;

public interface TrainRepository extends JpaRepository<Train, Long> {
}
