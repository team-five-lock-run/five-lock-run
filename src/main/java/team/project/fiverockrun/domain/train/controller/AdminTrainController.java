package team.project.fiverockrun.domain.train.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.project.fiverockrun.domain.train.dto.request.TrainRequest;
import team.project.fiverockrun.domain.train.dto.response.TrainReponse;
import team.project.fiverockrun.domain.train.service.TrainService;

@RestController
@RequestMapping("/admin/trains")
@RequiredArgsConstructor
public class AdminTrainController {

    private final TrainService trainService;

    // 열차 생성
    @PostMapping
    public ResponseEntity<TrainReponse> createStation (
            @Valid @RequestBody TrainRequest trainRequest
            ) {
        TrainReponse response = trainService.createTrain(trainRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
