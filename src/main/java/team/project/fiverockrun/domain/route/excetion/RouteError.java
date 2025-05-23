package team.project.fiverockrun.domain.route.excetion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import team.project.fiverockrun.common.exception.ErrorCode;

@Getter
@AllArgsConstructor
public enum RouteError implements ErrorCode {

    CANNOT_FIND_TRAIN(HttpStatus.NOT_FOUND ,"ROUTE_ERROR_001","존재하지 않는 기차입니다.")
    , CANNOT_FIND_STATION(HttpStatus.NOT_FOUND ,"ROUTE_ERROR_002","존재하지 않는 역입니다.")
    , ROUTE_DUPLICATED(HttpStatus.BAD_REQUEST, "ROUTE_ERROR_003", "이미 존재하는 노선입니다.")
    , CANNOT_FIND_ROUTE(HttpStatus.NOT_FOUND ,"ROUTE_ERROR_004","존재하지 않는 노선입니다.")
    , ALREADY_DEACTIVATE_ROUTE(HttpStatus.BAD_REQUEST ,"ROUTE_ERROR_005","이미 비활성화 된 노선입니다.")
    , ALREADY_ACTIVE_ROUTE(HttpStatus.BAD_REQUEST ,"ROUTE_ERROR_006","이미 활성화 된 노선입니다.")
    ;


    private final HttpStatus status;
    private final String code;
    private final String message;
}
