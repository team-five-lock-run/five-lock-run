package team.project.fiverockrun.domain.ticket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team.project.fiverockrun.domain.auth.security.CustomUserPrincipal;
import team.project.fiverockrun.domain.ticket.dto.response.TicketResponse;
import team.project.fiverockrun.domain.ticket.service.TicketService;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    // 티켓 단건 조회 (예매 기준)
    @GetMapping("/{bookingId}")
    public ResponseEntity<TicketResponse> getTicketByBookingId(@PathVariable Long bookingId,
                                                                @AuthenticationPrincipal CustomUserPrincipal principal) {
        Long userId = principal.getUser().getId();
        TicketResponse response = ticketService.getTicketByBookingId(bookingId, userId);
        return ResponseEntity.ok(response);
    }
}