package team.project.fiverockrun.domain.trainCar.entity;

import jakarta.persistence.*;
import lombok.Getter;
import team.project.fiverockrun.domain.train.entity.Train;
import team.project.fiverockrun.domain.trainCar.enums.CarType;

@Getter
@Entity
@Table(name = "train_car")
public class TrainCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;

    @Column(name = "car_number", nullable = false, unique = true)
    private Long carNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarType type;

}
