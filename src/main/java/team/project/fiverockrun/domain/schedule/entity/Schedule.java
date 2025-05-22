package team.project.fiverockrun.domain.schedule.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.project.fiverockrun.domain.station.entity.Station;
import team.project.fiverockrun.domain.train.entity.Train;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "schedule", uniqueConstraints =
    @UniqueConstraint(columnNames = {"train_id", "station_id", "date", "time"})
)
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;

    @ManyToOne
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;

    @Column(name = "date", nullable = false)
    private LocalDate departureDate;

    @Column(name = "time", nullable = false)
    private LocalTime departureTime;
}
