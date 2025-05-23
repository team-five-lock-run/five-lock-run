package team.project.fiverockrun.domain.reservation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import team.project.fiverockrun.common.exception.ErrorCode;

@Getter
@AllArgsConstructor
public enum ReservationError implements ErrorCode {

    ALREADY_RESERVED(HttpStatus.CONFLICT, "RESERVATION_ERROR_001", "이미 예약된 좌석입니다."),
    LOCK_FAIL(HttpStatus.TOO_MANY_REQUESTS, "RESERVATION_ERROR_002", "잠시 후 다시 시도해 주세요."),
    INTERRUPTED(HttpStatus.INTERNAL_SERVER_ERROR, "RESERVATION_ERROR_003", "예약 중 문제가 발생했습니다."),
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "RESERVATION_ERROR_004", "해당 예약이 존재하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
