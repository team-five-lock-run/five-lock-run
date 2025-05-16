package team.project.fiverockrun.domain.reservation.entity;

import jakarta.persistence.*;
import lombok.*;
import team.project.fiverockrun.common.entity.Timestamped;
import team.project.fiverockrun.domain.reservation.enums.ReservationStatus;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Reservation extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private String id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long seatId;

    @Column(nullable = false)
    private LocalDateTime paidAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus reservationStatus;

    @Column(nullable = false)
    private Integer seatPrice;

    public void cancelReservation() {
        this.reservationStatus = ReservationStatus.CANCELED;
    }

}
