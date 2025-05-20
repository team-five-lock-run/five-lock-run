package team.project.fiverockrun.domain.station.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.project.fiverockrun.domain.station.dto.response.StationResponse;
import team.project.fiverockrun.domain.station.entity.QStation;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StationRepositoryCustomImpl implements StationRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<StationResponse> findAllStation() {
        QStation station = QStation.station;

        return jpaQueryFactory
                .select(Projections.constructor(StationResponse.class, station.name, station.region))
                .from(station)
                .where(station.isActive.eq(true))
                .orderBy(station.region.asc(), station.name.asc())
                .fetch();
    }
}
