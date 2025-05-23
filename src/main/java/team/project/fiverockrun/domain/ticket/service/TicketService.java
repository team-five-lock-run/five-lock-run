package team.project.fiverockrun.domain.ticket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.project.fiverockrun.domain.booking.entity.Booking;
import team.project.fiverockrun.domain.booking.service.BookingService;
import team.project.fiverockrun.domain.station.entity.Station;
import team.project.fiverockrun.domain.station.repository.StationRepository;
import team.project.fiverockrun.domain.ticket.dto.response.TicketResponse;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final BookingService bookingService;
    private final StationRepository stationRepository;

    public TicketResponse getTicketByBookingId(Long bookingId, Long userId) {
        Booking booking = bookingService.getBooking(bookingId, userId);

        Station departureStation = stationRepository.findById(booking.getDepartureStationId())
            .orElseThrow(() -> new IllegalArgumentException("출발역을 찾을 수 없습니다."));
        Station arrivalStation = stationRepository.findById(booking.getArrivalStationId())
            .orElseThrow(() -> new IllegalArgumentException("도착역을 찾을 수 없습니다."));

        String ticketName = String.format("%s→%s - %s~%s - %s",
            departureStation.getName(),
            arrivalStation.getName(),
            booking.getDepartureTime().toLocalDate() + " " + booking.getDepartureTime().toLocalTime(),
            booking.getArrivalTime().toLocalTime(),
            booking.getSeat().getSeatNumber()
        );

        return TicketResponse.builder()
            .ticketName(ticketName)
            .trainName(booking.getTrain().getName())
            .carNumber(booking.getTrainCar().getCarNumber())
            .seatNumber(booking.getSeat().getSeatNumber())
            .price(booking.getSeatPrice())
            .paidAt(booking.getPaidAt())
            .build();
    }
}
