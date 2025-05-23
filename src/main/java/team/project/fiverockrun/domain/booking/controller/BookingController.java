package team.project.fiverockrun.domain.booking.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team.project.fiverockrun.domain.auth.security.CustomUserPrincipal;
import team.project.fiverockrun.domain.booking.dto.request.BookingRequest;
import team.project.fiverockrun.domain.booking.dto.response.BookingResponse;
import team.project.fiverockrun.domain.booking.entity.Booking;
import team.project.fiverockrun.domain.booking.service.BookingService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    // 예매 생성 (결제 완료건)
    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
        @Valid @RequestBody BookingRequest request,
        @AuthenticationPrincipal CustomUserPrincipal principal) {

        Long userId = principal.getUser().getId();
        BookingResponse response = bookingService.createBooking(request, userId);

        return ResponseEntity.ok(response);
    }

    // 사용자 예매 목록 조회
    @GetMapping("/user")
    public ResponseEntity<List<BookingResponse>> getBookingsByUser(@AuthenticationPrincipal CustomUserPrincipal principal) {

        Long userId = principal.getUser().getId();
        List<BookingResponse> responses = bookingService.getBookingResponsesByUserId(userId);

        return ResponseEntity.ok(responses);
    }

    // 예매 단건 조회
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> getBooking(
        @PathVariable Long bookingId,
        @AuthenticationPrincipal CustomUserPrincipal principal) {

        Long userId = principal.getUser().getId();
        BookingResponse response = bookingService.getBookingResponse(bookingId, userId);

        return ResponseEntity.ok(response);
    }

    // 예매 취소 (결제 상태 변경)
    @PatchMapping("/{bookingId}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId,
                                            @AuthenticationPrincipal CustomUserPrincipal principal) {

        Long userId = principal.getUser().getId();
        bookingService.cancelBooking(bookingId, userId);

        return ResponseEntity.noContent().build();
    }
}
