package team.project.fiverockrun.domain.waitLine.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import team.project.fiverockrun.domain.waitLine.dto.request.WaitLinesRequestDto;
import team.project.fiverockrun.domain.waitLine.model.WaitLine;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WaitLineService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final SseService sseService;
    private static final long MAX_QUEUE_SIZE = 10000;

    public String enterQueue(WaitLinesRequestDto requestDto) {
        Long userId = 1L; // todo: 지울 것
        String queueKey = buildQueueKey(requestDto);

        // 대기열 수 초과 체크
        Long size = redisTemplate.opsForList().size(queueKey);
        if(size != null && size >= MAX_QUEUE_SIZE) {
            return "full";
        }

        WaitLine waitLine = new WaitLine(
                userId,
                requestDto.getDate(),
                requestDto.getTime(),
                requestDto.getDepartureId(),
                requestDto.getArrivalId(),
                requestDto.getPeople()
        );

        // Redis List에 대기열 저장
        redisTemplate.opsForList().rightPush(queueKey, waitLine);

        // TTL Key 설정 (만료 이벤트 감지용)
        String ttlKey = "queue:ttl:" + userId;
        redisTemplate.opsForValue().set(ttlKey, queueKey, Duration.ofMinutes(5));

        // Backup Key 설정 (TTL 키가 만료되어 없어질 때 queue key 복원)
        String backupKey = "queue:backup:" + userId;
        redisTemplate.opsForValue().set(backupKey, queueKey, Duration.ofMinutes(5));
        return "enter & TTL";

    }

    public Long getPosition(Long userId, String queueKey) {
        List<Object> queue = redisTemplate.opsForList().range(queueKey, 0, -1);

        for(int i=0; i<queue.size(); i++) {
            WaitLine waitLine = (WaitLine) queue.get(i);
            if(waitLine.getUserId().equals(userId)) {
                return (long) (i+1); // 0-based index → 순번은 1부터 시작
            }
        }

        return -1L; // 대기열에 없음
    }

    public String leaveQueue(Long userId, String queueKey) {
        List<Object> queue = redisTemplate.opsForList().range(queueKey, 0, -1);
        if(queue == null || queue.isEmpty()) {
            return "empty";
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

                return "out of wait line & remove ttl";
            }
        }
        return "not exist";
    }

    private String buildQueueKey(WaitLinesRequestDto requestDto) {
        return "queue : " + requestDto.getDate() + " : " + requestDto.getTime() + " : " + requestDto.getDepartureId() + " : " + requestDto.getArrivalId();
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
