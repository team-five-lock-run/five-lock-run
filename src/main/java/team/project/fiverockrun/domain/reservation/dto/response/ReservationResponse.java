package team.project.fiverockrun.domain.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import team.project.fiverockrun.domain.reservation.model.Reservation;

@Getter
@Builder
@RequiredArgsConstructor
public class ReservationResponse {

    private final String key;
    private final Reservation reservation;

    public static ReservationResponse from(String key, Reservation reservation) {
        return ReservationResponse.builder()
                .key(key)
                .reservation(reservation)
                .build();
    }
}
