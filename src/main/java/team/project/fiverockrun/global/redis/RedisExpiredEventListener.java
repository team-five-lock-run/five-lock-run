package team.project.fiverockrun.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import team.project.fiverockrun.domain.waitLine.model.WaitLine;
import team.project.fiverockrun.domain.waitLine.service.WaitLineService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RedisExpiredEventListener implements MessageListener {

    private final RedisTemplate<String, Object> redisTemplate;
    private final WaitLineService waitLineService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();

        if(!expiredKey.startsWith("queue:ttl:")) { return;}

        Long userId = Long.valueOf(expiredKey.replace("queue:ttl:", ""));

        // TTL 키는 이미 만료되어 없어졌지만, 우리는 key 값을 value에 저장해놨었음
        // 따라서 backupKey로 만든 별도 저장소에서 queueKey 가져오기 (value 백업)
        String backupKey = "queue:backup:" + userId;
        String actualQueueKey = (String) redisTemplate.opsForValue().get(backupKey);

        if(actualQueueKey == null) { return; }

        removeUserFromQueue(userId, actualQueueKey);

        // TTL 만료 후 순번 알림 전송
        waitLineService.notifyAllUserPositions(actualQueueKey);

        // 백업 키 삭제
        redisTemplate.delete(backupKey);

    }

    private void removeUserFromQueue(Long userId, String queueKey) {
        List<Object> waitLines = redisTemplate.opsForList().range(queueKey, 0, -1);
        if(waitLines == null) { return; }

        for(Object obj : waitLines) {
            if(obj instanceof WaitLine waitLine && waitLine.getUserId().equals(userId)) {
                redisTemplate.opsForList().remove(queueKey, 1, waitLine);
                break;
            }
        }

    }

}