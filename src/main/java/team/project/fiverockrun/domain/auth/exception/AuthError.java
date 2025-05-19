package team.project.fiverockrun.domain.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import team.project.fiverockrun.common.exception.ErrorCode;

@Getter
@AllArgsConstructor
public enum AuthError implements ErrorCode { // 예시입니다.

    LOGIN_FAILED(HttpStatus.UNAUTHORIZED,"AUTH_ERROR_002","이메일 또는 비밀번호가 일치하지 않습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED,"AUTH_ERROR_004","만료된 토큰입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED,"AUTH_ERROR_005","유효하지 않은 토큰입니다."),
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN,"AUTH_ERROR_006","접근 권한이 없습니다."),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED,"AUTH_ERROR_007","토큰을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
