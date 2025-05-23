package team.project.fiverockrun.domain.booking.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.project.fiverockrun.domain.booking.dto.request.BookingRequest;
import team.project.fiverockrun.domain.booking.dto.response.BookingResponse;
import team.project.fiverockrun.domain.booking.entity.Booking;
import team.project.fiverockrun.domain.booking.enums.BookingStatus;
import team.project.fiverockrun.domain.booking.excetion.BookingError;
import team.project.fiverockrun.domain.booking.repository.BookingRepository;
import team.project.fiverockrun.domain.station.entity.Station;
import team.project.fiverockrun.domain.train.entity.Seat;
import team.project.fiverockrun.domain.train.entity.Train;
import team.project.fiverockrun.domain.train.entity.TrainCar;
import team.project.fiverockrun.domain.train.repository.SeatRepository;
import team.project.fiverockrun.domain.train.repository.TrainCarRepository;
import team.project.fiverockrun.domain.train.repository.TrainRepository;
import team.project.fiverockrun.domain.user.entity.User;
import team.project.fiverockrun.domain.user.repository.UserRepository;
import team.project.fiverockrun.common.exception.BaseException;
import team.project.fiverockrun.domain.station.repository.StationRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final SeatRepository seatRepository;
    private final TrainRepository trainRepository;
    private final TrainCarRepository trainCarRepository;
    private final StationRepository stationRepository;

    @Transactional
    public BookingResponse createBooking(BookingRequest request, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new BaseException(BookingError.USER_NOT_FOUND));

        Seat seat = seatRepository.findById(request.getSeatId())
            .orElseThrow(() -> new BaseException(BookingError.SEAT_NOT_FOUND));

        Train train = trainRepository.findById(request.getTrainId())
            .orElseThrow(() -> new BaseException(BookingError.TRAIN_NOT_FOUND));

        TrainCar trainCar = trainCarRepository.findById(request.getTrainCarId())
            .orElseThrow(() -> new BaseException(BookingError.TRAIN_CAR_NOT_FOUND));

        Booking booking = Booking.builder()
            .user(user)
            .seat(seat)
            .train(train)
            .trainCar(trainCar)
            .seatPrice(request.getSeatPrice())
            .paidAt(request.getPaidAt())
            .departureTime(request.getDepartureTime())
            .arrivalTime(request.getArrivalTime())
            .departureStationId(request.getDepartureStationId())
            .arrivalStationId(request.getArrivalStationId())
            .bookingStatus(BookingStatus.COMPLETED)
            .build();

        booking = bookingRepository.save(booking);

        Station departureStation = stationRepository.findById(booking.getDepartureStationId())
            .orElseThrow(() -> new BaseException(BookingError.STATION_NOT_FOUND));
        Station arrivalStation = stationRepository.findById(booking.getArrivalStationId())
            .orElseThrow(() -> new BaseException(BookingError.STATION_NOT_FOUND));

        return BookingResponse.builder()
            .bookingId(booking.getId())
            .trainName(train.getName())
            .carNumber(trainCar.getCarNumber())
            .seatNumber(seat.getSeatNumber())
            .price(booking.getSeatPrice())
            .paidAt(booking.getPaidAt())
            .departureStation(departureStation.getName())
            .departureTime(booking.getDepartureTime())
            .arrivalStation(arrivalStation.getName())
            .arrivalTime(booking.getArrivalTime())
            .build();
    }

    @Transactional
    public Booking getBooking(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new BaseException(BookingError.BOOKING_NOT_FOUND));
        if (!booking.getUser().getId().equals(userId)) {
            throw new BaseException(BookingError.USER_FORBIDDEN);
        }
        return booking;
    }
    public List<BookingResponse> getBookingResponsesByUserId(Long userId) {
        List<Booking> bookings = bookingRepository.findAll().stream()
            .filter(booking -> booking.getUser().getId().equals(userId))
            .toList();

        return bookings.stream().map(booking -> {
            Station departureStation = stationRepository.findById(booking.getDepartureStationId())
                .orElseThrow(() -> new BaseException(BookingError.STATION_NOT_FOUND));
            Station arrivalStation = stationRepository.findById(booking.getArrivalStationId())
                .orElseThrow(() -> new BaseException(BookingError.STATION_NOT_FOUND));

            return BookingResponse.builder()
                .bookingId(booking.getId())
                .trainName(booking.getTrain().getName())
                .carNumber(booking.getTrainCar().getCarNumber())
                .seatNumber(booking.getSeat().getSeatNumber())
                .price(booking.getSeatPrice())
                .paidAt(booking.getPaidAt())
                .departureStation(departureStation.getName())
                .departureTime(booking.getDepartureTime())
                .arrivalStation(arrivalStation.getName())
                .arrivalTime(booking.getArrivalTime())
                .build();
        }).toList();
    }

    @Transactional
    public BookingResponse getBookingResponse(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new BaseException(BookingError.BOOKING_NOT_FOUND));

        if (!booking.getUser().getId().equals(userId)) {
            throw new BaseException(BookingError.USER_FORBIDDEN);
        }

        Station departureStation = stationRepository.findById(booking.getDepartureStationId())
            .orElseThrow(() -> new BaseException(BookingError.STATION_NOT_FOUND));
        Station arrivalStation = stationRepository.findById(booking.getArrivalStationId())
            .orElseThrow(() -> new BaseException(BookingError.STATION_NOT_FOUND));

        return BookingResponse.builder()
            .bookingId(booking.getId())
            .trainName(booking.getTrain().getName())
            .carNumber(booking.getTrainCar().getCarNumber())
            .seatNumber(booking.getSeat().getSeatNumber())
            .price(booking.getSeatPrice())
            .paidAt(booking.getPaidAt())
            .departureStation(departureStation.getName())
            .departureTime(booking.getDepartureTime())
            .arrivalStation(arrivalStation.getName())
            .arrivalTime(booking.getArrivalTime())
            .build();
    }

    @Transactional
    public void cancelBooking(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new BaseException(BookingError.BOOKING_NOT_FOUND));

        if (!booking.getUser().getId().equals(userId)) {
            throw new BaseException(BookingError.USER_FORBIDDEN);
        }

        booking.cancelReservation();  // 상태 변경
    }

}
