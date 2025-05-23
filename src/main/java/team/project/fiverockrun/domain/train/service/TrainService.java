package team.project.fiverockrun.domain.train.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.project.fiverockrun.common.exception.BaseException;
import team.project.fiverockrun.domain.price.dto.response.SectionPriceResponse;
import team.project.fiverockrun.domain.price.service.SectionPriceService;
import team.project.fiverockrun.domain.route.repository.RouteRepository;
import team.project.fiverockrun.domain.schedule.entity.Schedule;
import team.project.fiverockrun.domain.schedule.repository.ScheduleRepository;
import team.project.fiverockrun.domain.station.entity.Station;
import team.project.fiverockrun.domain.station.repository.StationRepository;
import team.project.fiverockrun.domain.train.dto.request.TrainRequest;
import team.project.fiverockrun.domain.train.dto.request.TrainSerchRequest;
import team.project.fiverockrun.domain.train.dto.request.UpdateTrainRequest;
import team.project.fiverockrun.domain.train.dto.response.TrainCarResponse;
import team.project.fiverockrun.domain.train.dto.response.TrainReponse;
import team.project.fiverockrun.domain.train.dto.response.TrainSerchResponse;
import team.project.fiverockrun.domain.train.dto.response.UpdateTrainResponse;
import team.project.fiverockrun.domain.train.entity.Seat;
import team.project.fiverockrun.domain.train.entity.Train;
import team.project.fiverockrun.domain.train.entity.TrainCar;
import team.project.fiverockrun.domain.train.enums.SeatType;
import team.project.fiverockrun.domain.train.excetion.TrainError;
import team.project.fiverockrun.domain.train.repository.SeatRepository;
import team.project.fiverockrun.domain.train.repository.TrainCarRepository;
import team.project.fiverockrun.domain.train.repository.TrainRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainService {

    private final TrainRepository trainRepository;
    private final TrainCarRepository trainCarRepository;
    private final SeatRepository seatRepository;
    private final SectionPriceService sectionPriceService;
    private final RouteRepository routeRepository;
    private final StationRepository stationRepository;
    private final ScheduleRepository scheduleRepository;

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
    @Transactional
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
    @Transactional
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
    @Transactional
    public UpdateTrainResponse updateTrain(Long trainId, UpdateTrainRequest request) {
        Train train = trainRepository.findById(trainId)
                .orElseThrow(() -> new BaseException(TrainError.CANNOT_FIND_TRAIN));

        if (train.isActive()) {
            throw new BaseException(TrainError.CANNOT_EDIT_ACTIVE_TRAIN);
        }

        // 모든 값 dropB
        List<TrainCar> trainCars = trainCarRepository.findByTrainId(trainId);
        trainCarRepository.deleteAll(trainCars);
        trainCarRepository.flush();

        List<TrainCar> carList = new ArrayList<>();
        List<Seat> seatList = new ArrayList<>();

        // 차량 생성
        for (int carNumber = 1; carNumber <= request.getCarCount(); carNumber++) {
            SeatType seatType = (carNumber <= request.getPremiumCarCount())
                    ? SeatType.PREMIUM : SeatType.REGULAR;

            TrainCar trainCar = new TrainCar(carNumber, seatType, train);
            carList.add(trainCar);
        }

        trainCarRepository.saveAll(carList);
        trainCarRepository.flush(); // DB 강제 반영

        // 좌석 생성
        for (TrainCar trainCar : carList){
            for (int seatNumber = 1; seatNumber <= request.getSeatsCount(); seatNumber++) {
                char row = (char) ('A' + trainCar.getCarNumber() - 1);
                String seatNum = row + String.valueOf(seatNumber);
                Seat seat = new Seat(seatNum, trainCar);
                seatList.add(seat);
            }
        }

        seatRepository.saveAll(seatList);

        return new UpdateTrainResponse(
                request.getCarCount(),
                request.getSeatsCount(),
                request.getPremiumCarCount()
        );
    }

    // 열차 검색 후 결과 값 반환 (페이징X)
    public List<TrainSerchResponse> serchTrains(TrainSerchRequest request) {

        List<TrainSerchResponse> trainList = trainRepository.searchTrain(request);

        for (TrainSerchResponse train : trainList) {
            Long startOrder = getOrderByStation(train.getTrainId(), train.getDepartureStation());
            Long endOrder = getOrderByStation(train.getTrainId(), train.getArrivalStation());

            SectionPriceResponse priceResponse = sectionPriceService.getSectionPrices(train.getTrainId(), startOrder, endOrder);

            train.setPrices(priceResponse.getPrices());
        }

        return trainList;
    }


    private Long getOrderByStation(Long trainId, Long stationId) {
        return routeRepository.findByTrain_IdAndStation_Id(trainId, stationId)
                .orElseThrow(() -> new RuntimeException("해당 열차, 역 정차 정보 없음"))
                .getOrder();
    }

    public List<TrainCarResponse> searchTrainPremiumCars(
            Long trainId,
            Long departureStationId,
            Long arrivalStationId,
            LocalDate departureDate,
            int passengerCount
    ) {
        List<TrainCar> premiumCars = trainCarRepository.findByTrainIdAndSeatType(trainId, SeatType.PREMIUM);

        List<TrainCarResponse> result = new ArrayList<>();

        Station departureStation = stationRepository.findById(departureStationId)
                .orElseThrow(() -> new BaseException(TrainError.CANNOT_FIND_STATION));
        Station arrivalStation = stationRepository.findById(arrivalStationId)
                .orElseThrow(() -> new BaseException(TrainError.CANNOT_FIND_STATION));

        for (TrainCar car : premiumCars) {
            Schedule departureSchedule = scheduleRepository.findByTrainAndStationAndDepartureDate(
                    car.getTrain(), departureStation, departureDate
            ).orElseThrow(() -> new BaseException(TrainError.CANNOT_FIND_SCHEDULE));

            Schedule arrivalSchedule = scheduleRepository.findByTrainAndStationAndDepartureDate(
                    car.getTrain(), arrivalStation, departureDate
            ).orElseThrow(() -> new BaseException(TrainError.CANNOT_FIND_SCHEDULE));

            List<String> availableSeats = seatRepository.findAvailableSeats(
                    car.getId(), departureStationId, arrivalStationId, departureDate
            );

            if (availableSeats.size() >= passengerCount) {
                result.add(new TrainCarResponse(
                        departureStationId,
                        arrivalStationId,
                        departureDate,
                        departureSchedule.getDepartureTime(),
                        arrivalSchedule.getArrivalDate().atTime(arrivalSchedule.getArrivalTime()),
                        passengerCount,
                        car.getSeatType(),
                        car.getTrain().getTrainNumber(),
                        car.getCarNumber(),
                        availableSeats
                ));
            }
        }
        return result;
    }

    public List<TrainCarResponse> searchTrainRegularCars(
            Long trainId,
            Long departureStationId,
            Long arrivalStationId,
            LocalDate departureDate,
            int passengerCount
    ) {
        List<TrainCar> regularCars = trainCarRepository.findByTrainIdAndSeatType(trainId, SeatType.REGULAR);

        List<TrainCarResponse> result = new ArrayList<>();

        Station departureStation = stationRepository.findById(departureStationId)
                .orElseThrow(() -> new BaseException(TrainError.CANNOT_FIND_STATION));
        Station arrivalStation = stationRepository.findById(arrivalStationId)
                .orElseThrow(() -> new BaseException(TrainError.CANNOT_FIND_STATION));

        for (TrainCar car : regularCars) {
            Schedule departureSchedule = scheduleRepository.findByTrainAndStationAndDepartureDate(
                    car.getTrain(), departureStation, departureDate
            ).orElseThrow(() -> new BaseException(TrainError.CANNOT_FIND_SCHEDULE));

            Schedule arrivalSchedule = scheduleRepository.findByTrainAndStationAndDepartureDate(
                    car.getTrain(), arrivalStation, departureDate
            ).orElseThrow(() -> new BaseException(TrainError.CANNOT_FIND_SCHEDULE));

            List<String> availableSeats = seatRepository.findAvailableSeats(
                    car.getId(), departureStationId, arrivalStationId, departureDate
            );

            if (availableSeats.size() >= passengerCount) {
                result.add(new TrainCarResponse(
                        departureStationId,
                        arrivalStationId,
                        departureDate,
                        departureSchedule.getDepartureTime(),
                        arrivalSchedule.getArrivalDate().atTime(arrivalSchedule.getArrivalTime()),
                        passengerCount,
                        car.getSeatType(),
                        car.getTrain().getTrainNumber(),
                        car.getCarNumber(),
                        availableSeats
                ));
            }
        }
        return result;
    }


}
