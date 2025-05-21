package team.project.fiverockrun.domain.train.excetion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import team.project.fiverockrun.common.exception.ErrorCode;

@Getter
@AllArgsConstructor
public enum TrainError implements ErrorCode {

    TRAIN_NUMBER_ALREADY_EXISTS(HttpStatus.CONFLICT ,"TRAIN_ERROR_001","이미 등록된 기차 번호입니다")
    ;


    private final HttpStatus status;
    private final String code;
    private final String message;
}
