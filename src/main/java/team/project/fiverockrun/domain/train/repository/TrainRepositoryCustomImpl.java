package team.project.fiverockrun.domain.train.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.project.fiverockrun.domain.price.entity.QSectionPrice;
import team.project.fiverockrun.domain.route.entity.QRoute;
import team.project.fiverockrun.domain.schedule.entity.QSchedule;
import team.project.fiverockrun.domain.station.entity.QStation;
import team.project.fiverockrun.domain.train.dto.request.TrainRequest;
import team.project.fiverockrun.domain.train.dto.request.TrainSerchRequest;
import team.project.fiverockrun.domain.train.dto.response.TrainSerchResponse;
import team.project.fiverockrun.domain.train.entity.QTrain;


import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TrainRepositoryCustomImpl implements TrainRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<TrainSerchResponse> searchTrain(TrainSerchRequest request) {

        QSchedule depSchedule = new QSchedule("depSchedule");
        QSchedule arrSchedule = new QSchedule("arrSchedule");

        QRoute depRoute = new QRoute("depRoute");
        QRoute arrRoute = new QRoute("arrRoute");

        QStation depStation = new QStation("depStation");
        QStation arrStation = new QStation("arrStation");

        QTrain train = QTrain.train;

        List<TrainSerchResponse> results = jpaQueryFactory
                .select(Projections.constructor(TrainSerchResponse.class,
                        depSchedule.departureDate,
                        train.id,
                        train.type.stringValue(),
                        train.trainNumber,
                        depStation.id,
                        arrStation.id,
                        depSchedule.departureDate,
                        depSchedule.departureTime,
                        arrSchedule.arrivalDate,
                        arrSchedule.arrivalTime
                ))
                .from(depSchedule)
                .join(train).on(train.id.eq(depSchedule.train.id))
                .join(depRoute).on(depRoute.train.id.eq(train.id)
                        .and(depRoute.station.id.eq(request.getDepartureStation())))
                .join(arrRoute).on(arrRoute.train.id.eq(train.id)
                        .and(arrRoute.station.id.eq(request.getArrivalStation())))
                .join(depStation).on(depStation.id.eq(depRoute.station.id))
                .join(arrStation).on(arrStation.id.eq(arrRoute.station.id))
                .join(arrSchedule).on(arrSchedule.train.id.eq(train.id)
                        .and(arrSchedule.station.id.eq(arrRoute.station.id))
                        .and(arrSchedule.departureDate.eq(depSchedule.departureDate)))
                .where(depSchedule.station.id.eq(depRoute.station.id)
                        .and(depSchedule.departureDate.eq(request.getDepartureDate()))
                        .and(depRoute.order.lt(arrRoute.order))
                        .and(depSchedule.departureTime.loe(arrSchedule.arrivalTime))
                        .and(depSchedule.departureTime.goe(request.getDepartureTime())) // 검색 시간 이후 스케줄
                )
                .fetch();

        return results;
    }
}
