package team.project.fiverockrun.domain.price.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.project.fiverockrun.domain.price.dto.request.SectionPriceRequest;
import team.project.fiverockrun.domain.price.dto.request.UpdateSectionPriceRequest;
import team.project.fiverockrun.domain.price.dto.response.SectionPriceResponse;
import team.project.fiverockrun.domain.price.service.SectionPriceService;

@RestController
@RequestMapping("/admin/section-prices")
@RequiredArgsConstructor
public class AdminSectionPriceController {

    private final SectionPriceService sectionPriceService;

    @PostMapping
    public ResponseEntity<SectionPriceResponse> saveSectionPrice(@RequestBody @Valid SectionPriceRequest request) {
        SectionPriceResponse response = sectionPriceService.saveSectionPrice(
                request.getTrainId(),
                request.getStartOrder(),
                request.getEndOrder(),
                request.getSeatType(),
                request.getPrice()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{sectionPriceId}")
    public ResponseEntity<SectionPriceResponse> updateSectionPrice(
            @PathVariable Long sectionPriceId,
            @RequestBody @Valid UpdateSectionPriceRequest request
    ) {
        SectionPriceResponse response = sectionPriceService.updateSectionPrice(sectionPriceId, request.getPrice());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{sectionPriceId}")
    public ResponseEntity<Void> deleteSectionPrice(@PathVariable Long sectionPriceId) {
        sectionPriceService.deleteSectionPrice(sectionPriceId);
        return ResponseEntity.noContent().build();
    }
}
