package team.project.fiverockrun.domain.schedule.excetion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import team.project.fiverockrun.common.exception.ErrorCode;

@Getter
@AllArgsConstructor
public enum ScheduleError implements ErrorCode {

    ROUTE_NOT_FOUND_TRAIN(HttpStatus.NOT_FOUND ,"SCHEDULE_ERROR_001","루트가 존재하지 않는 열차입니다.")
    , CANNOT_FIND_TRAIN(HttpStatus.NOT_FOUND ,"SCHEDULE_ERROR_002","존재하지 않는 기차입니다.")
    ;


    private final HttpStatus status;
    private final String code;
    private final String message;
}
