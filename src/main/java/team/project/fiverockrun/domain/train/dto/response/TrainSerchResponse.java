package team.project.fiverockrun.domain.train.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TrainSerchResponse {

    private String trainType;          // 열차 종류
    private String trainNumber;        // 열차 번호
    private String departureStation;   // 출발역
    private String arrivalStation;     // 도착역
    private LocalDateTime departureTime; // 출발시간
    private LocalDateTime arrivalTime;   // 도착시간
    private int regularPrice;          // 일반실 가격
    private int premiumPrice;          // 특실 가격

}
