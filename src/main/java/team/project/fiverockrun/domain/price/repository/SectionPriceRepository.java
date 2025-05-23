package team.project.fiverockrun.domain.price.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.project.fiverockrun.domain.price.entity.SectionPrice;
import team.project.fiverockrun.domain.train.enums.SeatType;

import java.util.List;

public interface SectionPriceRepository extends JpaRepository<SectionPrice, Long> {

    @Query("""
                select p from SectionPrice p
                where p.train.id = :trainId
                and p.startOrder >= :startOrder
                and p.endOrder <= :endOrder
                and p.seatType = :seatType
                order by p.startOrder asc
            """)
    List<SectionPrice> findSectionPrices(
            @Param("trainId") Long trainId,
            @Param("startOrder") Long StartOrder,
            @Param("endOrder") Long endOrder,
            @Param("seatType") SeatType seatType
    );

    boolean existsByTrain_IdAndStartOrderAndEndOrderAndSeatType(
            Long trainId, Long startOrder, Long endOrder, SeatType seatType);
}
