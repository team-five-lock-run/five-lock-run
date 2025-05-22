package team.project.fiverockrun.domain.price.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import team.project.fiverockrun.common.exception.ErrorCode;

@Getter
@AllArgsConstructor
public enum PriceError implements ErrorCode {

    INVALID_SECTION_ORDER(HttpStatus.BAD_REQUEST, "PRICE_ERROR_001", "출발역 순서는 도착역보다 앞서야 합니다."),
    SECTION_PRICE_NOT_FOUND(HttpStatus.NOT_FOUND, "PRICE_ERROR_002", "해당 구간에 대한 가격 정보가 존재하지 않습니다."),
    SECTION_PRICE_ALREADY_EXISTS(HttpStatus.CONFLICT, "PRICE_ERROR_003", "이미 동일한 구간과 좌석 등급의 가격 정보가 존재합니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
