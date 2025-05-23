package team.project.fiverockrun.domain.reservation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team.project.fiverockrun.domain.auth.security.CustomUserPrincipal;
import team.project.fiverockrun.domain.reservation.dto.request.ReservationRequest;
import team.project.fiverockrun.domain.reservation.dto.response.ReservationResponse;
import team.project.fiverockrun.domain.reservation.service.ReservationService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> reserveSeat(@RequestBody @Valid ReservationRequest request,
                                                           @AuthenticationPrincipal CustomUserPrincipal principal) {

        Long userId = principal.getUser().getId();
        ReservationResponse response = reservationService.reserveSeatWithLock(request, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/user")
    public ResponseEntity<List<ReservationResponse>> getReservationsByUser(@AuthenticationPrincipal CustomUserPrincipal principal) {
        Long userId = principal.getUser().getId();
        List<ReservationResponse> responses = reservationService.getReservationsByUser(userId);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/seat/{trainId}/{carId}/{seatId}")
    public ResponseEntity<List<ReservationResponse>> getReservationsBySeat(@PathVariable Long trainId,
                                                                           @PathVariable Long carId,
                                                                           @PathVariable Long seatId) {
        List<ReservationResponse> responses = reservationService.getReservationsBySeat(trainId, carId, seatId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/details/{trainId}/{carId}/{seatId}/{departureStationId}/{arrivalStationId}")
    public ResponseEntity<ReservationResponse> getReservation(@PathVariable Long trainId,
                                                              @PathVariable Long carId,
                                                              @PathVariable Long seatId,
                                                              @PathVariable Long departureStationId,
                                                              @PathVariable Long arrivalStationId,
                                                              @RequestParam LocalDateTime departureTime,
                                                              @RequestParam LocalDateTime arrivalTime) {
        ReservationResponse response = reservationService.getReservation(
                trainId, carId, seatId, departureStationId, arrivalStationId, departureTime, arrivalTime
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{trainId}/{carId}/{seatId}/{departureStationId}/{arrivalStationId}")
    ResponseEntity<Void> deleteReservation(@AuthenticationPrincipal CustomUserPrincipal principal,
                                           @PathVariable Long trainId,
                                           @PathVariable Long carId,
                                           @PathVariable Long seatId,
                                           @PathVariable Long departureStationId,
                                           @PathVariable Long arrivalStationId,
                                           @RequestParam LocalDateTime departureTime,
                                           @RequestParam LocalDateTime arrivalTime) {
        Long userId = principal.getUser().getId();
        reservationService.deleteReservation(
                userId, trainId, carId, seatId, departureStationId, arrivalStationId, departureTime, arrivalTime
        );

        return ResponseEntity.noContent().build();
    }

}
