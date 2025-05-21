package team.project.fiverockrun.domain.train.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.project.fiverockrun.common.exception.BaseException;
import team.project.fiverockrun.domain.train.dto.request.TrainRequest;
import team.project.fiverockrun.domain.train.dto.response.TrainReponse;
import team.project.fiverockrun.domain.train.entity.Seat;
import team.project.fiverockrun.domain.train.entity.Train;
import team.project.fiverockrun.domain.train.entity.TrainCar;
import team.project.fiverockrun.domain.train.enums.SeatType;
import team.project.fiverockrun.domain.train.excetion.TrainError;
import team.project.fiverockrun.domain.train.repository.SeatRepository;
import team.project.fiverockrun.domain.train.repository.TrainCarRepository;
import team.project.fiverockrun.domain.train.repository.TrainRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainService {

    private final TrainRepository trainRepository;
    private final TrainCarRepository trainCarRepository;
    private final SeatRepository seatRepository;

    // 열차, 차량, 좌석 생성
    @Transactional
    public TrainReponse createTrain(TrainRequest trainRequest) {

        if (trainRepository.findByTrainNumber(trainRequest.getTrainNumber()).isPresent()) {
            throw new BaseException(TrainError.TRAIN_NUMBER_ALREADY_EXISTS);
        }

        // 열차 생성
        Train newTrain = new Train(
                trainRequest.getTrainNumber(),
                trainRequest.getName(),
                trainRequest.getType()
        );

        Train saveTrain = trainRepository.save(newTrain);

        // 차량 생성
        List<TrainCar> carList = new ArrayList<>();

        for (int carNumber = 1; carNumber <= trainRequest.getCarCount(); carNumber++) {
            SeatType seatType = (carNumber <= trainRequest.getPremiumCarCount())
                    ? SeatType.PREMIUM : SeatType.REGULAR;

            TrainCar trainCar = new TrainCar(carNumber, seatType, newTrain);
            trainCarRepository.save(trainCar);

            // 좌석 생성
            for (int seatNumber = 1; seatNumber <= trainRequest.getSeatsCount(); seatNumber++) {
                char row = (char) ('A' + carNumber - 1);
                String seatNum = row + String.valueOf(seatNumber);
                Seat seat = new Seat(seatNum, trainCar);
                seatRepository.save(seat);
            }

            carList.add(trainCar);

        }

        return new TrainReponse(
                saveTrain.getTrainNumber(),
                saveTrain.getName(),
                saveTrain.getType(),
                trainRequest.getCarCount(),
                trainRequest.getSeatsCount(),
                trainRequest.getPremiumCarCount()
        );
    }

    // 열차 비활성화
    public void deactivateTrain(Long trainId) {
        Train train = trainRepository.findById(trainId)
                .orElseThrow(() -> new BaseException(TrainError.CANNOT_FIND_TRAIN));

        if (!train.isActive()) {
            throw new BaseException(TrainError.ALREADY_DEACTIVATE_TRAIN);
        }

        train.setActive(false);
        trainRepository.save(train);
    }

    // 열차 활성화
    public void activateTrain(Long trainId) {
        Train train = trainRepository.findById(trainId)
                .orElseThrow(() -> new BaseException(TrainError.CANNOT_FIND_TRAIN));

        if (train.isActive()) {
            throw new BaseException(TrainError.ALREADY_ACTIVE_TRAIN);
        }

        train.setActive(true);
        trainRepository.save(train);
    }

    // 열차에 대한 차량, 좌석 수정

}
