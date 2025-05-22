package team.project.fiverockrun.domain.waitLine.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import team.project.fiverockrun.domain.auth.security.CustomUserPrincipal;
import team.project.fiverockrun.domain.waitLine.dto.request.WaitLineRequestDto;
import team.project.fiverockrun.domain.waitLine.dto.response.WaitLineResponseDto;
import team.project.fiverockrun.domain.waitLine.service.WaitLineService;
import team.project.fiverockrun.domain.waitLine.service.SseService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/waitLines")
public class WaitLineController {

    private final WaitLineService waitLineService;
    private final SseService sseService;

    @PostMapping("/enter")
    public ResponseEntity<WaitLineResponseDto> enterQueue(
            @AuthenticationPrincipal CustomUserPrincipal customUserPrincipal,
            @RequestBody WaitLineRequestDto requestDto
    ) {
        return ResponseEntity.ok(waitLineService.enterQueue(customUserPrincipal.getUser().getId(), requestDto));
    }

    @GetMapping("/position")
    public ResponseEntity<Long> getPosition(
            @AuthenticationPrincipal CustomUserPrincipal customUserPrincipal,
            @RequestParam String key
    ) {
        return ResponseEntity.ok(waitLineService.getPosition(customUserPrincipal.getUser().getId(), key));
    }

    @PostMapping("/leave")
    public ResponseEntity<WaitLineResponseDto> leaveQueue(
            @AuthenticationPrincipal CustomUserPrincipal customUserPrincipal,
            @RequestParam String key
    ) {
        return ResponseEntity.ok(waitLineService.leaveQueue(customUserPrincipal.getUser().getId(), key));
    }

    @GetMapping(value="/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@AuthenticationPrincipal CustomUserPrincipal customUserPrincipal) {
        return sseService.subscribe(customUserPrincipal.getUser().getId());
    }

    @PostMapping("/complete")
    public ResponseEntity<WaitLineResponseDto> complete(
            @AuthenticationPrincipal CustomUserPrincipal customUserPrincipal,
            @RequestParam String key
    ) {
        return ResponseEntity.ok(waitLineService.completeQueue(customUserPrincipal.getUser().getId(), key));
    }



}