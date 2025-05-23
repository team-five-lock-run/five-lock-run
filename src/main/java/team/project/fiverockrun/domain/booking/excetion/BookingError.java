package team.project.fiverockrun.domain.booking.excetion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import team.project.fiverockrun.common.exception.ErrorCode;

@Getter
@AllArgsConstructor
public enum BookingError implements ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "BOOKING_ERROR_001","사용자를 찾을 수 없습니다."),
    SEAT_NOT_FOUND(HttpStatus.NOT_FOUND, "BOOKING_ERROR_002","좌석을 찾을 수 없습니다."),
    TRAIN_NOT_FOUND(HttpStatus.NOT_FOUND, "BOOKING_ERROR_003","기차를 찾을 수 없습니다."),
    TRAIN_CAR_NOT_FOUND(HttpStatus.NOT_FOUND, "BOOKING_ERROR_004","기차 칸을 찾을 수 없습니다."),
    BOOKING_NOT_FOUND(HttpStatus.NOT_FOUND, "BOOKING_ERROR_005", "예매를 찾을 수 없습니다."),
    USER_FORBIDDEN(HttpStatus.FORBIDDEN, "BOOKING_ERROR_006", "접근 권한이 없습니다."),
    STATION_NOT_FOUND(HttpStatus.NOT_FOUND, "BOOKING_ERROR_OO7", "해당 역을 찾을 수 없습니다");


    private final HttpStatus status;
    private final String code;
    private final String message;
}