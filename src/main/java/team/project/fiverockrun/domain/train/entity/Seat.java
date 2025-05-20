package team.project.fiverockrun.domain.train.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@Table(name = "seat")
@NoArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "train_car_id", nullable = false)
    private TrainCar trainCar;

    @Column(name = "seat_number", nullable = false, unique = true)
    private String seatNumber;

    public Seat(String seatNumber, TrainCar trainCar) {
        this.seatNumber = seatNumber;
        this.trainCar = trainCar;
    }
}
