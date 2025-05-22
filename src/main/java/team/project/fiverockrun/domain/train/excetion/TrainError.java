package team.project.fiverockrun.domain.train.excetion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import team.project.fiverockrun.common.exception.ErrorCode;

@Getter
@AllArgsConstructor
public enum TrainError implements ErrorCode {

    TRAIN_NUMBER_ALREADY_EXISTS(HttpStatus.CONFLICT ,"TRAIN_ERROR_001","이미 등록된 기차 번호입니다.")
    , CANNOT_EDIT_ACTIVE_TRAIN(HttpStatus.CONFLICT ,"TRAIN_ERROR_002","활성화된 기차는 수정할 수 없습니다.")
    , CANNOT_FIND_TRAIN(HttpStatus.NOT_FOUND ,"TRAIN_ERROR_003","존재하지 않는 기차입니다.")
    , ALREADY_DEACTIVATE_TRAIN(HttpStatus.BAD_REQUEST ,"TRAIN_ERROR_004","이미 비활성화 된 기차입니다.")
    , ALREADY_ACTIVE_TRAIN(HttpStatus.BAD_REQUEST ,"TRAIN_ERROR_005","이미 활성화 된 기차입니다.")
    ;


    private final HttpStatus status;
    private final String code;
    private final String message;
}
