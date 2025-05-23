package team.project.fiverockrun.domain.train.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.project.fiverockrun.domain.route.entity.QRoute;
import team.project.fiverockrun.domain.schedule.entity.QSchedule;
import team.project.fiverockrun.domain.station.entity.QStation;
import team.project.fiverockrun.domain.train.dto.request.TrainRequest;
import team.project.fiverockrun.domain.train.dto.response.TrainSerchResponse;
import team.project.fiverockrun.domain.train.entity.QTrain;


import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TrainRepositoryCustomImpl implements TrainRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<TrainSerchResponse> searchTrain(TrainRequest request) {

        QSchedule schedule = QSchedule.schedule;
        QTrain train = QTrain.train;
        QStation depStaion = new QStation("depStation");
        QStation arrStaion = new QStation("arrStation");
        QRoute route = QRoute.route;


        return jpaQueryFactory
                .select(Projections.constructor(TrainSerchResponse.class,
                                schedule.departureDate,
                                train.id,
                                train.type.stringValue(),
                                train.trainNumber,
                                depStaion.id,
                                arrStaion.id,
                                schedule.departureDate,
                                schedule.departureTime,
                                schedule.arrivalDate,
                                schedule.arrivalTime,

                        )
                );



        return List.of();
    }
}
