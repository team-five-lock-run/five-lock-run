package team.project.fiverockrun.domain.reservation.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.project.fiverockrun.domain.reservation.dto.request.ReservationRequest;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation implements Serializable {

    private Long trainId;

    private Long carId;

    private Long seatId;

    private Long departureStationId;

    private Long arrivalStationId;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime departureTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime arrivalTime;

    private Integer seatPrice;

    private Long userId;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime reservedAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime expireAt;

    public static Reservation of(ReservationRequest dto, Long userId) {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        return new Reservation(
                dto.getTrainId(),
                dto.getCarId(),
                dto.getSeatId(),
                dto.getDepartureStationId(),
                dto.getArrivalStationId(),
                dto.getDepartureTime(),
                dto.getArrivalTime(),
                dto.getSeatPrice(),
                userId,
                now,
                now.plusMinutes(10)
        );
    }
}
