package team.project.fiverockrun.domain.price.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import team.project.fiverockrun.domain.train.enums.SeatType;

@Getter
public class SectionPriceRequest {

    @NotNull(message = "열차 ID는 필수입니다.")
    private Long trainId;

    @NotNull(message = "출발역의 정차 순서는 필수입니다.")
    @Min(value = 1, message = "출발 순서는 1 이상이어야 합니다.")
    private Long startOrder;

    @NotNull(message = "도착역의 정차 순서는 필수입니다.")
    @Min(value = 2, message = "도착 순서는 2 이상이어야 합니다.")
    private Long endOrder;

    @NotNull(message = "좌석 등급은 필수입니다.")
    private SeatType seatType;

    @NotNull(message = "가격은 필수입니다.")
    @Min(value = 0, message = "가격은 0원 이상이어야 합니다.")
    private Integer price;
}
