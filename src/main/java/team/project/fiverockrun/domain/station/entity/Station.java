package team.project.fiverockrun.domain.station.entity;

import jakarta.persistence.*;
import lombok.Setter;

@Setter
@Entity
@Table(name = "station")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String region;

}
