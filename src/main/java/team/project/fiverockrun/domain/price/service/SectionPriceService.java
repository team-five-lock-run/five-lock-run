package team.project.fiverockrun.domain.price.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.project.fiverockrun.common.exception.BaseException;
import team.project.fiverockrun.domain.price.dto.response.SectionPriceResponse;
import team.project.fiverockrun.domain.price.entity.SectionPrice;
import team.project.fiverockrun.domain.price.exception.PriceError;
import team.project.fiverockrun.domain.price.repository.SectionPriceRepository;
import team.project.fiverockrun.domain.train.entity.Train;
import team.project.fiverockrun.domain.train.enums.SeatType;
import team.project.fiverockrun.domain.train.excetion.TrainError;
import team.project.fiverockrun.domain.train.repository.TrainRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SectionPriceService {

    private final SectionPriceRepository priceRepository;
    private final TrainRepository trainRepository;

    /**
     * 구간별 좌석 가격 정책 등록
     *
     * @param trainId 열차 ID
     * @param startOrder 출발역 정차 순서
     * @param endOrder 도착역 정차 순서
     * @param seatType 좌석 등급
     * @param price 가격
     * @return 등록된 가격 정책 정보
     */
    @Transactional
    public SectionPriceResponse saveSectionPrice(Long trainId, Long startOrder, Long endOrder, SeatType seatType, int price) {
        Train train = trainRepository.findById(trainId)
                .orElseThrow(() -> new BaseException(TrainError.CANNOT_FIND_TRAIN));

        if (startOrder >= endOrder) {
            throw new BaseException(PriceError.INVALID_SECTION_ORDER);
        }

        boolean exists = priceRepository.existsByTrain_IdAndStartOrderAndEndOrderAndSeatType(
                trainId, startOrder, endOrder, seatType
        );

        if (exists) {
            throw new BaseException(PriceError.SECTION_PRICE_ALREADY_EXISTS);
        }

        SectionPrice sectionPrice = new SectionPrice(train, startOrder, endOrder, seatType, price);
        SectionPrice saved = priceRepository.save(sectionPrice);

        return SectionPriceResponse.from(saved);
    }

    /**
     * 구간별 가격 수정
     *
     * @param sectionPriceId 수정할 가격 정책 ID
     * @param price 수정할 가격
     * @return 수정된 가격 정보
     */
    @Transactional
    public SectionPriceResponse updateSectionPrice(Long sectionPriceId, int price) {
        SectionPrice sectionPrice = priceRepository.findById(sectionPriceId)
                .orElseThrow(() -> new BaseException(PriceError.SECTION_PRICE_NOT_FOUND));

        sectionPrice.updatePrice(price);

        return SectionPriceResponse.from(sectionPrice);
    }

    /**
     * 구간별 가격 삭제
     *
     * @param sectionPriceId 삭제할 가격 정책 ID
     */
    @Transactional
    public void deleteSectionPrice(Long sectionPriceId) {
        SectionPrice sectionPrice = priceRepository.findById(sectionPriceId)
                .orElseThrow(() -> new BaseException(PriceError.SECTION_PRICE_NOT_FOUND));

        priceRepository.delete(sectionPrice);
    }

    /**
     * 특정 열차의 구간별 좌석 등급별 가격 정보 조회
     * - 목록 조회 시 사용되며, 출발역과 도착역의 정차 순서를 기준으로 좌석 등급별 가격 정보를 제공합니다.
     *
     * @param trainId 열차 ID
     * @param startOrder 출발역의 정차 순서
     * @param endOrder 도착역의 정차 순서
     * @return 해당 열차 구간의 좌석 등급별 가격 정보
     * {
     *   trainId: 119,
     *   startOrder: 1,
     *   endOrder: 3,
     *   prices: {
     *     REGULAR: 15000,
     *     PREMIUM: 22000
     *   }
     * }
     */
    public SectionPriceResponse getSectionPrices(Long trainId, Long startOrder, Long endOrder) {
        Map<SeatType, Integer> prices = new HashMap<>();

        for (SeatType seatType : SeatType.values()) {
            try {
                int price = getSectionSeatTypePrice(trainId, startOrder, endOrder, seatType);
                prices.put(seatType, price);
            } catch (BaseException e) {
                log.warn("{} 열차의 좌석 등급 {}의 가격 정보를 찾을 수 없습니다: {}", trainId, seatType, e.getMessage());
            }
        }

        return SectionPriceResponse.builder()
                .trainId(trainId)
                .startOrder(startOrder)
                .endOrder(endOrder)
                .prices(prices)
                .build();
    }

    /**
     * 특정 열차의 구간별 좌석 등급 가격 계산
     *
     * @param trainId 열차 ID
     * @param startOrder 출발역의 정차 순서
     * @param endOrder 도착역의 정차 순서
     * @param seatType 좌석 등급 (REGULAR, PREMIUM)
     * @return 해당 구간의 좌석 등급 가격
     */
    public int getSectionSeatTypePrice(Long trainId, Long startOrder, Long endOrder, SeatType seatType) {
        if (startOrder >= endOrder) {
            throw new BaseException(PriceError.INVALID_SECTION_ORDER);
        }

        List<SectionPrice> sectionPrices = priceRepository.findSectionPrices(trainId, startOrder, endOrder, seatType);

        if (sectionPrices.isEmpty()) {
            throw new BaseException(PriceError.SECTION_PRICE_NOT_FOUND);
        }

        return sectionPrices.stream()
                .mapToInt(SectionPrice::getPrice)
                .sum();
    }
}
