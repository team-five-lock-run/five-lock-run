package team.project.fiverockrun.domain.train.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "seat", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"train_car_id", "seat_number"})
})
@NoArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "train_car_id", nullable = false)
    private TrainCar trainCar;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;

    public Seat(String seatNumber, TrainCar trainCar) {
        this.seatNumber = seatNumber;
        this.trainCar = trainCar;
    }
}
