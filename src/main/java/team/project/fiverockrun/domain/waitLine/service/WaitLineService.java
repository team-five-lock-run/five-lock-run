package team.project.fiverockrun.domain.waitLine.service;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import team.project.fiverockrun.domain.waitLine.dto.request.WaitLineRequestDto;
import team.project.fiverockrun.domain.waitLine.dto.response.PositionResponseDto;
import team.project.fiverockrun.domain.waitLine.dto.response.WaitLineResponseDto;
import team.project.fiverockrun.domain.waitLine.model.WaitLine;

import javax.swing.text.Position;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class WaitLineService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final SseService sseService;
    private final RedissonClient redissonClient;
    private static final long MAX_QUEUE_SIZE = 1000;
    private static final long LOCK_WAIT_TIME = 3L; // 초
    private static final long LOCK_LEASE_TIME = 5L; // 초

    public WaitLineResponseDto enterQueue(Long userId, WaitLineRequestDto requestDto) {
        String queueKey = buildQueueKey(requestDto);
        String lockKey = "lock:" + queueKey;
        RLock lock = redissonClient.getLock(lockKey);

        boolean acquired = false;
        try {
            acquired = lock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS);
            if (!acquired) {
                return new WaitLineResponseDto(false, "Failed to acquire lock", queueKey);
            }

            Long size = redisTemplate.opsForList().size(queueKey);
            if (size != null && size >= MAX_QUEUE_SIZE) {
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

            redisTemplate.opsForList().rightPush(queueKey, waitLine);
            redisTemplate.opsForValue().set("queue:ttl:" + userId, queueKey, Duration.ofMinutes(5));
            redisTemplate.opsForValue().set("queue:backup:" + userId, queueKey, Duration.ofMinutes(6));

            return new WaitLineResponseDto(true, "enter of waiting line", queueKey);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new WaitLineResponseDto(false, "lock interrupted", queueKey);
        } finally {
            if (acquired && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    public PositionResponseDto getPosition(Long userId, String queueKey) {
        List<Object> queue = redisTemplate.opsForList().range(queueKey, 0, -1);
        for (int i = 0; i < queue.size(); i++) {
            WaitLine waitLine = (WaitLine) queue.get(i);
            if (waitLine.getUserId().equals(userId)) {
                return new PositionResponseDto((long) (i + 1), (long) queue.size());
            }
        }
        return new PositionResponseDto(-1L, (long) queue.size());
    }

    public WaitLineResponseDto leaveQueue(Long userId, String queueKey) {
        List<Object> queue = redisTemplate.opsForList().range(queueKey, 0, -1);
        if (queue == null || queue.isEmpty()) {
            return new WaitLineResponseDto(false, "empty of waiting line", queueKey);
        }
        for (Object obj : queue) {
            if (obj instanceof WaitLine waitLine && waitLine.getUserId().equals(userId)) {
                redisTemplate.opsForList().remove(queueKey, 1, waitLine);
                redisTemplate.delete("queue:ttl:" + userId);
                redisTemplate.delete("queue:backup:" + userId);
                notifyAllUserPositions(queueKey);
                return new WaitLineResponseDto(true, "leave of waiting line", queueKey);
            }
        }
        return new WaitLineResponseDto(false, "not exist user in waiting line", queueKey);
    }

    public WaitLineResponseDto completeQueue(Long userId, String queueKey) {
        List<Object> queue = redisTemplate.opsForList().range(queueKey, 0, -1);
        if (queue == null || queue.isEmpty()) {
            return new WaitLineResponseDto(false, "not exist information of waiting line", queueKey);
        }
        for (Object obj : queue) {
            if (obj instanceof WaitLine waitLine && waitLine.getUserId().equals(userId)) {
                redisTemplate.opsForList().remove(queueKey, 1, waitLine);
                redisTemplate.delete("queue:ttl:" + userId);
                redisTemplate.delete("queue:backup:" + userId);
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

    public void notifyAllUserPositions(String queueKey) {
        List<Object> queue = redisTemplate.opsForList().range(queueKey, 0, -1);
        if (queue == null) return;
        for (int i = 0; i < queue.size(); i++) {
            Object obj = queue.get(i);
            if (obj instanceof WaitLine waitLine) {
                Long userId = waitLine.getUserId();
                Long position = (long) (i + 1);
                sseService.notifyUserPosition(userId, position);
            }
        }
    }
}
