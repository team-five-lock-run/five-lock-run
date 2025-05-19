package team.project.fiverockrun.domain.seat.entity;

import jakarta.persistence.*;
import lombok.Getter;
import team.project.fiverockrun.domain.station.entity.Station;
import team.project.fiverockrun.domain.trainCar.entity.TrainCar;

@Getter
@Entity
@Table(name = "seat")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "train_car_id", nullable = false)
    private TrainCar trainCar;

    @Column(name = "seat_number", nullable = false, unique = true)
    private String seatNumber;

}
