package team.project.fiverockrun.domain.price.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.project.fiverockrun.domain.train.entity.Train;
import team.project.fiverockrun.domain.train.enums.SeatType;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SectionPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_order", nullable = false)
    private Long startOrder;

    @Column(name = "end_order", nullable = false)
    private Long endOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "seat_type", nullable = false)
    private SeatType seatType;

    @Column(nullable = false)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;

    public SectionPrice(Train train, Long startOrder, Long endOrder, SeatType seatType, int price) {
        this.train = train;
        this.startOrder = startOrder;
        this.endOrder = endOrder;
        this.seatType = seatType;
        this.price = price;
    }

    public void updatePrice(int price) {
        this.price = price;
    }
}
