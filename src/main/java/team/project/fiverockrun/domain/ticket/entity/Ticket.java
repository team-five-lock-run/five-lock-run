package team.project.fiverockrun.domain.ticket.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.project.fiverockrun.domain.user.entity.User;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "train_name", nullable = false)
    private String trainName; // 기차 이름: KTX123, 무궁화456

    @Column(name = "car_number", nullable = false)
    private Integer carNumber; // 기차 차량 번호: 3

    @Column(name = "seat_number", nullable = false)
    private String seatNumber; // 좌석 번호: 9C

    @Column(name = "paid_at", nullable = false)
    private LocalDateTime paidAt; // 결제 완료 일시

    @Column(name = "departure_station", nullable = false)
    private String departureStation; // 출발역: 서울

    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime; // 출발 일시

    @Column(name = "arrival_station", nullable = false)
    private String arrivalStation; // 도착역: 부산

    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime; // 도착 일시

    @Column(nullable = false)
    private Integer price; // 결제 금액

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 예매자
}
