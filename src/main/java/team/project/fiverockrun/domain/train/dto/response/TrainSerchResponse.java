package team.project.fiverockrun.domain.train.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.project.fiverockrun.domain.train.enums.SeatType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrainSerchResponse {

    private LocalDate date;
    private Long trainId;
    private String trainType;          // 열차 종류
    private Long trainNumber;        // 열차 번호
    private Long departureStation;   // 출발역
    private Long arrivalStation;     // 도착역
    private LocalDate departureDate;    // 출발날짜
    private LocalTime departureTime;    // 출발시간
    private LocalDate arrivalDate;
    private LocalTime arrivalTime;   // 도착시간

    @Setter
    private Map<SeatType, Integer> prices; // 좌석 등급별 가격

    // QueryDSL용 생성자 (파라미터 순서, 타입 동일)
    public TrainSerchResponse(LocalDate date, Long trainId, String trainType, Long trainNumber,
                              Long departureStation, Long arrivalStation,
                              LocalDate departureDate, LocalTime departureTime,
                              LocalDate arrivalDate, LocalTime arrivalTime) {
        this.date = date;
        this.trainId = trainId;
        this.trainType = trainType;
        this.trainNumber = trainNumber;
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
    }

}
