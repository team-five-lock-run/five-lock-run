package team.project.fiverockrun.domain.reservation.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReservationRequest {

    @NotNull(message = "기차 ID는 필수입니다.")
    private Long trainId;

    @NotNull(message = "차량 ID는 필수입니다.")
    private Long carId;

    @NotNull(message = "좌석 ID는 필수입니다.")
    private Long seatId;

    @NotNull(message = "출발역 ID는 필수입니다.")
    private Long departureStationId;

    @NotNull(message = "도착역 ID는 필수입니다.")
    private Long arrivalStationId;

    @NotNull(message = "출발 일시는 필수입니다.")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime departureTime;

    @NotNull(message = "도착 일시는 필수입니다.")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime arrivalTime;

    @NotNull(message = "금액은 필수입니다.")
    private Integer seatPrice;
}
