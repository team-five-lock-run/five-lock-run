package team.project.fiverockrun.domain.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import team.project.fiverockrun.common.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum UserError implements ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_ERROR_001", "사용자를 찾을 수 없습니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "USER_ERROR_002", "올바르지 않은 비밀번호입니다."),
    DELETED_USER(HttpStatus.BAD_REQUEST, "USER_ERROR_003", "탈퇴한 사용자입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}

