package team.project.fiverockrun.domain.waitLine.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class SseService {

    // 사용자 ID를 기준으로 SseEmitter 저장
    // 현재 연결 중인 사용자들의 emitter 저장소
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    // 클라이언트가 subscribe 요청 시 연결 생성 및 저장
    public SseEmitter subscribe(Long userId) {

        if(emitters.containsKey(userId)) {
            emitters.remove(userId).complete();
        }

        SseEmitter emitter = new SseEmitter(10 * 60 * 1000L); // 10분 동안 연결 유지
        emitters.put(userId, emitter);

        // emitter 제거
        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> emitters.remove(userId));
        emitter.onError((e) -> emitters.remove(userId));

        try {
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("SSE connection complete"));
        } catch (IOException e) {
            emitter.completeWithError(e);
        }

        return emitter;
    }

    // 서버가 특정 userId의 순번을 실시간으로 전송할 때 호출
    public void notifyUserPosition(Long userId, Long position) {
        SseEmitter emitter = emitters.get(userId);
        if(emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("queue-position")
                        .data("Now your position : " + position));
            } catch (IOException e) {
                emitters.remove(userId);
                emitter.completeWithError(e);
            }
        }
    }
}
