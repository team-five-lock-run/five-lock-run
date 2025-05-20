package team.project.fiverockrun.domain.route.entity;

import jakarta.persistence.*;
import lombok.Getter;
import team.project.fiverockrun.domain.station.entity.Station;
import team.project.fiverockrun.domain.train.entity.Train;

@Entity
@Getter
@Table(name = "route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;

    @ManyToOne
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;

    @Column(name = "orders", nullable = false)
    private Long order;

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled = true;
}
