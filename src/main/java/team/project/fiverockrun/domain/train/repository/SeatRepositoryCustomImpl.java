package team.project.fiverockrun.domain.train.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import team.project.fiverockrun.domain.train.entity.QSeat;
import team.project.fiverockrun.domain.train.enums.SeatType;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class SeatRepositoryCustomImpl implements SeatRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findAvailableSeats(Long id, Long departureStation, Long arrivalStation, LocalDate departureDate) {
        QSeat seat = QSeat.seat;
//        QReservation reservation = QReservation.reservation;
//
//        return queryFactory
//                .select(seat.seatNumber)
//                .from(seat)
//                .where(seat.trainCar.train.id.eq(trainId))
//                .where(seat.trainCar.trainCarType.eq(SeatType.PREMIUM)) // PREMIUM만 조회
//                .where(seat.notIn(
//                        // 이미 예약된 좌석 제외
//                        queryFactory
//                                .select(reservation.seat)
//                                .from(reservation)
//                                .where(reservation.train.id.eq(trainId))
//                                .where(reservation.departureDate.eq(departureDate))
//                                // 여기서 구간이 겹치는 예약도 필터링해야 함
//                                .where(
//                                        reservation.departureStation.id.lt(arrivalStationId)
//                                                .and(reservation.arrivalStation.id.gt(departureStationId))
//                                )
//                ))
//                .fetch();
        return queryFactory
                .select(seat.seatNumber)
                .from(seat)
                .where(seat.trainCar.id.eq(id))
                .fetch();

    }
}
