package team.project.fiverockrun.domain.price.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.project.fiverockrun.domain.price.dto.response.SectionPriceResponse;
import team.project.fiverockrun.domain.price.service.SectionPriceService;
import team.project.fiverockrun.domain.train.enums.SeatType;

/**
 * 가격 조회 테스트용
 */
@RestController
@RequestMapping("/section-prices/train")
@RequiredArgsConstructor
public class TestSectionPriceController {

    private final SectionPriceService sectionPriceService;

    @GetMapping
    public ResponseEntity<SectionPriceResponse> getSectionPrices(@RequestParam Long trainId,
                                                                 @RequestParam int startOrder,
                                                                 @RequestParam int endOrder) {
        SectionPriceResponse response = sectionPriceService.getSectionPrices(trainId, startOrder, endOrder);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/seat-type")
    public ResponseEntity<Integer> getSectionSeatTypePrice(@RequestParam Long trainId,
                                                           @RequestParam int startOrder,
                                                           @RequestParam int endOrder,
                                                           @RequestParam SeatType seatType) {
        int price = sectionPriceService.getSectionSeatTypePrice(trainId, startOrder, endOrder, seatType);
        return ResponseEntity.ok(price);
    }
}
