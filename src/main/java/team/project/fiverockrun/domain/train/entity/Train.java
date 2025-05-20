package team.project.fiverockrun.domain.train.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.project.fiverockrun.common.entity.Timestamped;
import team.project.fiverockrun.domain.train.enums.TrainType;

@Getter
@Entity
@Table(name = "train")
@NoArgsConstructor
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "train_number", nullable = false, unique = true)
    private Long trainNumber;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrainType type;

    public Train(Long trainNumber, String name, TrainType type) {
        this.trainNumber = trainNumber;
        this.name = name;
        this.type = type;
    }

}
