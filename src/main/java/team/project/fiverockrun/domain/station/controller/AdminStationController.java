package team.project.fiverockrun.domain.station.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.project.fiverockrun.domain.station.dto.request.StationRequest;
import team.project.fiverockrun.domain.station.dto.response.StationResponse;
import team.project.fiverockrun.domain.station.service.StationService;

import java.util.List;

@RestController
@RequestMapping("/admin/stations")
@RequiredArgsConstructor
public class AdminStationController {

    private final StationService stationService;

    // 역 생성
    @PostMapping
    public ResponseEntity<StationResponse> createStation (
            @Valid @RequestBody StationRequest stationRequest
            ) {
        StationResponse response = stationService.createStation(stationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 역 비활성화
    @PatchMapping("/{id}/disable")
    public ResponseEntity<Void> disableStation(
            @PathVariable Long id
    ) {
        stationService.disableStation(id);
        return ResponseEntity.noContent().build();
    }
}
