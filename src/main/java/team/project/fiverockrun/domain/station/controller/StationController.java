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
@RequestMapping("/stations")
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    // 역 조회
    @GetMapping
    public ResponseEntity<List<StationResponse>> getAllStation() {
        return ResponseEntity.ok(stationService.getAllStation());
    }
}
