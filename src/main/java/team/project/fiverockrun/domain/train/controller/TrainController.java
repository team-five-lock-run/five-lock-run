package team.project.fiverockrun.domain.train.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.project.fiverockrun.domain.train.dto.request.TrainRequest;
import team.project.fiverockrun.domain.train.dto.request.TrainSerchRequest;
import team.project.fiverockrun.domain.train.dto.response.TrainCarResponse;
import team.project.fiverockrun.domain.train.dto.response.TrainReponse;
import team.project.fiverockrun.domain.train.dto.response.TrainSerchResponse;
import team.project.fiverockrun.domain.train.service.TrainService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/trains")
@RequiredArgsConstructor
public class TrainController {

    private final TrainService trainService;

    // 열차 검색
    @GetMapping
    public ResponseEntity<List<TrainSerchResponse>> serchTrain (
            @Valid @RequestBody TrainSerchRequest request
    ) {
        List<TrainSerchResponse> trains = trainService.serchTrains(request);
        return ResponseEntity.ok(trains);
    }

    // 열차 상세 검색(프리미엄)
    @GetMapping("/{trainId}/premium")
    public ResponseEntity<List<TrainCarResponse>> searchTrainCarPremium (
            @PathVariable Long trainId,
            @RequestParam Long departureStation,
            @RequestParam Long arrivalStation,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
            @RequestParam(defaultValue = "1") int passengerCount
    ) {
        List<TrainCarResponse> premiumCars = trainService.searchTrainPremiumCars(
                trainId, departureStation, arrivalStation, departureDate, passengerCount
        );
        return ResponseEntity.ok(premiumCars);
    }

    // 열차 상세 검색(레귤러)
    @GetMapping("/{trainId}/regular")
    public ResponseEntity<List<TrainCarResponse>> searchTrainCarRegular (
            @PathVariable Long trainId,
            @RequestParam Long departureStation,
            @RequestParam Long arrivalStation,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
            @RequestParam(defaultValue = "1") int passengerCount
    ) {
        List<TrainCarResponse> regularCars = trainService.searchTrainRegularCars(
                trainId, departureStation, arrivalStation, departureDate, passengerCount
        );
        return ResponseEntity.ok(regularCars);
    }


}
