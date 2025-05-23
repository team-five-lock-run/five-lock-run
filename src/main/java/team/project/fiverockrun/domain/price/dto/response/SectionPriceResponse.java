package team.project.fiverockrun.domain.price.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import team.project.fiverockrun.domain.price.entity.SectionPrice;
import team.project.fiverockrun.domain.train.enums.SeatType;

import java.util.Map;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SectionPriceResponse {

    private Long trainId;
    private Long startOrder;
    private Long endOrder;
    private Map<SeatType, Integer> prices;

    public static SectionPriceResponse from(SectionPrice sectionPrice) {
        return SectionPriceResponse.builder()
                .trainId(sectionPrice.getTrain().getId())
                .startOrder(sectionPrice.getStartOrder())
                .endOrder(sectionPrice.getEndOrder())
                .prices(Map.of(sectionPrice.getSeatType(), sectionPrice.getPrice()))
                .build();
    }
}
