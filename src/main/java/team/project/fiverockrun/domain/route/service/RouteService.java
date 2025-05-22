package team.project.fiverockrun.domain.route.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.project.fiverockrun.common.exception.BaseException;
import team.project.fiverockrun.domain.route.dto.request.RouteRequest;
import team.project.fiverockrun.domain.route.dto.response.RouteResponse;
import team.project.fiverockrun.domain.route.entity.Route;
import team.project.fiverockrun.domain.route.excetion.RouteError;
import team.project.fiverockrun.domain.route.repository.RouteRepository;
import team.project.fiverockrun.domain.station.entity.Station;
import team.project.fiverockrun.domain.station.repository.StationRepository;
import team.project.fiverockrun.domain.train.entity.Train;
import team.project.fiverockrun.domain.train.excetion.TrainError;
import team.project.fiverockrun.domain.train.repository.TrainRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteService{

    private final RouteRepository routeRepository;
    private final TrainRepository trainRepository;
    private final StationRepository stationRepository;

    // 루트 생성
    @Transactional
    public Route createRoute(RouteRequest request) {

        try{
            Train train = trainRepository.findById(request.getTrainId())
                    .orElseThrow(() -> new BaseException(RouteError.CANNOT_FIND_TRAIN));

            Station station = stationRepository.findById(request.getStationId())
                    .orElseThrow(() -> new BaseException(RouteError.CANNOT_FIND_STATION));

            Route route = new Route(
                    train,
                    station,
                    request.getOrder()
            );

            return routeRepository.save(route);
        } catch (DataIntegrityViolationException e) {
            throw new BaseException(RouteError.ROUTE_DUPLICATED);
        }
    }

    // 루트 조회
    @Transactional(readOnly = true)
    public List<RouteResponse> getRouteByTrain(Long trainId) {

        Train train = trainRepository.findById(trainId)
                .orElseThrow(() -> new BaseException(RouteError.CANNOT_FIND_TRAIN));

        List<Route> routes = routeRepository.findByTrainIdOrderByOrderAsc(trainId);

        return routes.stream()
                .map(route -> new RouteResponse(
                        route.getStation().getName(),
                        route.getTrain().getId(),
                        route.getOrder(),
                        route.getIsEnabled()
                ))
                .collect(Collectors.toList());
    }

    // 루트 비활성화
    @Transactional
    public void deactivateRoute(Long routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new BaseException(TrainError.CANNOT_FIND_TRAIN));

        if (!route.getIsEnabled()) {
            throw new BaseException(RouteError.ALREADY_DEACTIVATE_ROUTE);
        }

        route.setEnabled(false);
        routeRepository.save(route);
    }

    // 루트 활성화
    @Transactional
    public void activateRoute(Long routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new BaseException(TrainError.CANNOT_FIND_TRAIN));

        if (route.getIsEnabled()) {
            throw new BaseException(RouteError.ALREADY_ACTIVE_ROUTE);
        }

        route.setEnabled(true);
        routeRepository.save(route);
    }


}
