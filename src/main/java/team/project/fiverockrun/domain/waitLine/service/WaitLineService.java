package team.project.fiverockrun.domain.waitLine.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import team.project.fiverockrun.domain.waitLine.dto.request.WaitLineRequestDto;
import team.project.fiverockrun.domain.waitLine.dto.response.PositionResponseDto;
import team.project.fiverockrun.domain.waitLine.dto.response.WaitLineResponseDto;
import team.project.fiverockrun.domain.waitLine.model.WaitLine;

import javax.swing.text.Position;
import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WaitLineService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final SseService sseService;
    private static final long MAX_QUEUE_SIZE = 10000;

    public WaitLineResponseDto enterQueue(Long userId, WaitLineRequestDto requestDto) {
        String queueKey = buildQueueKey(requestDto);

        // 대기열 수 초과 체크
        Long size = redisTemplate.opsForList().size(queueKey);
        if(size != null && size >= MAX_QUEUE_SIZE) {
            return new WaitLineResponseDto(false, "full of waiting line", queueKey);
        }

        WaitLine waitLine = new WaitLine(
                userId,
                requestDto.getDepartureDate(),
                requestDto.getDepartureTime(),
                requestDto.getArrivalDateTime(),
                requestDto.getDepartureStation(),
                requestDto.getArrivalStation(),
                requestDto.getPassengerCount()
        );

        // Redis List에 대기열 저장
        redisTemplate.opsForList().rightPush(queueKey, waitLine);

        // TTL Key 설정 (만료 이벤트 감지용)
        String ttlKey = "queue:ttl:" + userId;
        redisTemplate.opsForValue().set(ttlKey, queueKey, Duration.ofMinutes(5));

        // Backup Key 설정 (TTL 키가 만료되어 없어질 때 queue key 복원)
        String backupKey = "queue:backup:" + userId;
        redisTemplate.opsForValue().set(backupKey, queueKey, Duration.ofMinutes(5));

        return new WaitLineResponseDto(true, "enter of waiting line", queueKey);
    }

    public PositionResponseDto getPosition(Long userId, String queueKey) {
        List<Object> queue = redisTemplate.opsForList().range(queueKey, 0, -1);

        for(int i=0; i<queue.size(); i++) {
            WaitLine waitLine = (WaitLine) queue.get(i);
            if(waitLine.getUserId().equals(userId)) {
                return new PositionResponseDto((long) (i+1), (long) queue.size()); // 0-based index → 순번은 1부터 시작
            }
        }
        return new PositionResponseDto(-1L, (long) queue.size()); // 대기열에 없음
    }

    public WaitLineResponseDto leaveQueue(Long userId, String queueKey) {
        List<Object> queue = redisTemplate.opsForList().range(queueKey, 0, -1);
        if(queue == null || queue.isEmpty()) {
            return new WaitLineResponseDto(false, "empty of waiting line", queueKey);
        }

        for(Object obj : queue) {
            if(obj instanceof WaitLine waitLine && waitLine.getUserId().equals(userId)) {
                redisTemplate.opsForList().remove(queueKey, 1, waitLine);

                String ttlKey = "queue:ttl:" + userId;
                redisTemplate.delete(ttlKey);

                String backupKey = "queue:backup:" + userId;
                redisTemplate.delete(backupKey);

                // 사용자 나간 후 순번 재계산 및 알림
                notifyAllUserPositions(queueKey);

                return new WaitLineResponseDto(true, "leave of waiting line", queueKey);
            }
        }
        return new WaitLineResponseDto(false, "not exist user in waiting line", queueKey);
    }

    public WaitLineResponseDto completeQueue(Long userId, String queueKey) {
        List<Object> queue = redisTemplate.opsForList().range(queueKey, 0, -1);
        if(queue == null || queue.isEmpty()) {
            return new WaitLineResponseDto(false, "not exist information of waiting line", queueKey);
        }

        for(Object obj : queue) {
            if(obj instanceof WaitLine waitLine && waitLine.getUserId().equals(userId)) {
                // Redis List에서 사용자 제거
                redisTemplate.opsForList().remove(queueKey, 1, waitLine);

                // TTL 키 및 백업 키 제거
                redisTemplate.delete("queue:ttl:" + userId);
                redisTemplate.delete("queue:backup:" + userId);

                // 실시간 순번 알림 전송
                notifyAllUserPositions(queueKey);

                return new WaitLineResponseDto(true, "complete reservation", queueKey);
            }
        }

        return new WaitLineResponseDto(false, "not exist user in waiting line", queueKey);
    }

    private String buildQueueKey(WaitLineRequestDto requestDto) {
        return "queue:" + requestDto.getDepartureDate()
                + ":" + requestDto.getDepartureTime()
                + ":" + requestDto.getArrivalDateTime()
                + ":" + requestDto.getDepartureStation()
                + ":" + requestDto.getArrivalStation();
    }

    // 순번 재계산 메서드
    public void notifyAllUserPositions(String queueKey) {
        List<Object> queue = redisTemplate.opsForList().range(queueKey, 0, -1);
        if(queue == null) { return; }

        for(int i=0; i<queue.size(); i++) {
            Object obj = queue.get(i);
            if(obj instanceof WaitLine waitLine) {
                Long userId = waitLine.getUserId();
                Long position = (long) (i+1);
                sseService.notifyUserPosition(userId, position);
            }
        }
    }

}
