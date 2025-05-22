package team.project.fiverockrun.domain.train.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.project.fiverockrun.domain.train.dto.request.TrainRequest;
import team.project.fiverockrun.domain.train.dto.request.UpdateTrainRequest;
import team.project.fiverockrun.domain.train.dto.response.TrainReponse;
import team.project.fiverockrun.domain.train.dto.response.UpdateTrainResponse;
import team.project.fiverockrun.domain.train.service.TrainService;

@RestController
@RequestMapping("/admin/trains")
@RequiredArgsConstructor
public class AdminTrainController {

    private final TrainService trainService;

    // 열차(차량, 좌석) 생성
    @PostMapping
    public ResponseEntity<TrainReponse> createStation (
            @Valid @RequestBody TrainRequest trainRequest
            ) {
        TrainReponse response = trainService.createTrain(trainRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 열차 비활성화
    @PatchMapping("/{id}/deactive")
    public ResponseEntity<Void> deactivateTrain (
            @PathVariable Long id
    ) {
        trainService.deactivateTrain(id);
        return ResponseEntity.ok().build();
    }

    // 열차 활성화
    @PatchMapping("/{id}/active")
    public ResponseEntity<Void> activateTrain (
            @PathVariable Long id
    ) {
        trainService.activateTrain(id);
        return ResponseEntity.ok().build();
    }

    // 열차 (차량, 좌석) 수정
    @PutMapping("/{id}")
    public ResponseEntity<UpdateTrainResponse> createStation (
            @PathVariable Long id,
            @Valid @RequestBody UpdateTrainRequest request
    ) {
        UpdateTrainResponse response = trainService.updateTrain(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
