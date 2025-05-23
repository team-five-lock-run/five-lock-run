package team.project.fiverockrun.domain.route.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.project.fiverockrun.domain.station.entity.Station;
import team.project.fiverockrun.domain.train.entity.Train;

@Entity
@Getter
@Table(name = "route", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"train_id", "station_id"}),     // 같은 열차에 같은 역 두 번 금지
        @UniqueConstraint(columnNames = {"train_id", "orders"})          // 같은 열차에 같은 순번 두 번 금지
})
@NoArgsConstructor
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;

    @ManyToOne
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;

    @Column(name = "orders", nullable = false)
    private Long order;

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled = true;

    public Route(Train train, Station station, Long order) {
        this.train = train;
        this.station = station;
        this.order = order;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }
}
