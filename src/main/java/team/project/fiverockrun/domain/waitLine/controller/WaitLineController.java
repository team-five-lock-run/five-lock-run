package team.project.fiverockrun.domain.waitLine.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import team.project.fiverockrun.domain.waitLine.dto.request.WaitLinesRequestDto;
import team.project.fiverockrun.domain.waitLine.service.WaitLineService;
import team.project.fiverockrun.domain.waitLine.service.SseService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/waitLines")
public class WaitLineController {

    private final WaitLineService waitLineService;
    private final SseService sseService;

    @PostMapping("/enter")
    public ResponseEntity<String> enterQueue(@RequestBody WaitLinesRequestDto requestDto) {
        return ResponseEntity.ok(waitLineService.enterQueue(requestDto));
    }

    @GetMapping("/position")
    public ResponseEntity<Long> getPosition(@RequestParam Long userId, @RequestParam String key) {
        return ResponseEntity.ok(waitLineService.getPosition(userId, key));
    }

    @PostMapping("/leave")
    public ResponseEntity<String> leaveQueue(@RequestParam Long userId, @RequestParam String key) {
        return ResponseEntity.ok(waitLineService.leaveQueue(userId, key));
    }

    @GetMapping(value="/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(Long userId) {
        return sseService.subscribe(userId);
    }



}
