package team.project.fiverockrun.domain.train.dto.response;

import lombok.Getter;
import team.project.fiverockrun.domain.train.enums.SeatType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
public class TrainCarResponse {

    private Long departureStation;    // 출발역
    private Long arrivalStation;      // 도착역
    private LocalDate departureDate;    // 출발 날짜
    private LocalTime departureTime;    // 출발 시간
    private LocalDateTime arrivalDateTime;  // 도착시각
    private int passengerCount; // 탑승객
    private SeatType seatType;  // 차량 타입

    private Long trainNum;    // 기차 번호
    private int trainCar;   // 기차 차량
    private List<String> seat;    // 좌석

    public TrainCarResponse(
            Long departureStation,
            Long arrivalStation,
            LocalDate departureDate,
            LocalTime departureTime,
            LocalDateTime arrivalDateTime,
            int passengerCount,
            SeatType seatType,
            Long trainNum,
            int trainCar,
            List<String> seat
    ) {
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDateTime = arrivalDateTime;
        this.passengerCount = passengerCount;
        this.seatType = seatType;
        this.trainNum = trainNum;
        this.trainCar = trainCar;
        this.seat = seat;
    }

}
