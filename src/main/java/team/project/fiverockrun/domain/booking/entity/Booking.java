package team.project.fiverockrun.domain.booking.entity;

import jakarta.persistence.*;
import lombok.*;
import team.project.fiverockrun.common.entity.Timestamped;
import team.project.fiverockrun.domain.booking.enums.BookingStatus;

import java.time.LocalDateTime;
import team.project.fiverockrun.domain.train.entity.Seat;
import team.project.fiverockrun.domain.train.entity.Train;
import team.project.fiverockrun.domain.train.entity.TrainCar;
import team.project.fiverockrun.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Booking extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_car_id", nullable = false)
    private TrainCar trainCar;

    @Column(nullable = false)
    private LocalDateTime paidAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus bookingStatus;

    @Column(nullable = false)
    private Integer seatPrice;

    public void cancelReservation() {
        this.bookingStatus = BookingStatus.CANCELED;
    }

}
