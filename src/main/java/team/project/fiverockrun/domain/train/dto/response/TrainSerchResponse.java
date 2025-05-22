package team.project.fiverockrun.domain.train.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrainSerchResponse {

    private LocalDate date;
    private Long trainId;
    private String trainType;          // 열차 종류
    private String trainNumber;        // 열차 번호
    private Long departureStation;   // 출발역
    private Long arrivalStation;     // 도착역
    private LocalDate departureDate;    // 출발날짜
    private LocalTime departureTime;    // 출발시간
    private LocalDateTime arrivalTime;   // 도착시간
    private int regularPrice;          // 일반실 가격
    private int premiumPrice;          // 특실 가격

}
