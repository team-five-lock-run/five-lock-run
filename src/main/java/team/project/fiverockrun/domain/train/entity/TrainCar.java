package team.project.fiverockrun.domain.train.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.project.fiverockrun.domain.train.enums.SeatType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "train_car")
@NoArgsConstructor
public class TrainCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;

    @Column(name = "car_number", nullable = false, unique = true)
    private int carNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatType seatType;

    @OneToMany(mappedBy = "trainCar", cascade = CascadeType.ALL)
    private List<Seat> seats = new ArrayList<>();

    public TrainCar(int carNumber, SeatType seatType, Train train) {
        this.carNumber = carNumber;
        this.seatType = seatType;
        this.train = train;
    }
}
