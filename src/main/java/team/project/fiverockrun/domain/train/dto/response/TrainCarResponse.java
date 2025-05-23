package team.project.fiverockrun.domain.train.dto.response;

import lombok.Getter;
import team.project.fiverockrun.domain.train.enums.SeatType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class TrainCarResponse {

    private Long departureStation;    // 출발역
    private Long arrivalStation;      // 도착역
    private LocalDate departureDate;    // 출발 날짜
    private LocalTime departureTime;    // 출발 시간
    private LocalDateTime arrivalDateTime;  // 도착시각
    private int passengerCount; // 탑승객
    private SeatType seatType;  // 차량 타입

    private int tainNum;    // 기차 번호
    private int trainCar;   // 기차 차량
    private String seat;    // 좌석

}
