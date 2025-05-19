package team.project.fiverockrun.domain.station.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.project.fiverockrun.domain.station.dto.request.StationRequest;
import team.project.fiverockrun.domain.station.dto.response.StationResponse;
import team.project.fiverockrun.domain.station.service.StationService;

@RestController
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    // 역 생성
    @PostMapping("/admin/stations")
    public ResponseEntity<StationResponse> createStation (
            @Valid @RequestBody StationRequest stationRequest
            ) {
        StationResponse response = stationService.createStation(stationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
