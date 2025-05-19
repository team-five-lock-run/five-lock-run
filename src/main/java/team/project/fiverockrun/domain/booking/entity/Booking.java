package team.project.fiverockrun.domain.booking.entity;

import jakarta.persistence.*;
import lombok.*;
import team.project.fiverockrun.common.entity.Timestamped;
import team.project.fiverockrun.domain.booking.enums.BookingStatus;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Booking extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long seatId;

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
