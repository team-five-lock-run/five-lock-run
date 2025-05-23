package team.project.fiverockrun.domain.route.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.project.fiverockrun.domain.route.dto.request.RouteRequest;
import team.project.fiverockrun.domain.route.dto.response.RouteResponse;
import team.project.fiverockrun.domain.route.entity.Route;
import team.project.fiverockrun.domain.route.service.RouteService;

import java.util.List;

@RestController
@RequestMapping("/admin/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    // 루트 생성
    @PostMapping
    public ResponseEntity<Route> createRoute(
            @Valid @RequestBody RouteRequest request
            ) {
        Route route =  routeService.createRoute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(route);
    }

    // 루트 조회
    @GetMapping("/train/{trainId}")
    public ResponseEntity<List<RouteResponse>> getRoutesByTrain(
            @PathVariable Long trainId
    ) {
        List<RouteResponse> responses = routeService.getRouteByTrain(trainId);
        return ResponseEntity.ok(responses);
    }

    // 열차 비활성화
    @PatchMapping("/{id}/deactive")
    public ResponseEntity<Void> deactivateRoute (
            @PathVariable Long id
    ) {
        routeService.deactivateRoute(id);
        return ResponseEntity.ok().build();
    }

    // 열차 활성화
    @PatchMapping("/{id}/active")
    public ResponseEntity<Void> activateRoute (
            @PathVariable Long id
    ) {
        routeService.activateRoute(id);
        return ResponseEntity.ok().build();
    }


}
