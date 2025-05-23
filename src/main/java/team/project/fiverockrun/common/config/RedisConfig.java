package team.project.fiverockrun.common.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.client.RestTemplate;
import team.project.fiverockrun.common.redis.RedisExpiredEventListener;

@Configuration
public class RedisConfig {

    // 1. 문자열만 저장할 때 사용하는 템플릿 (락 등 단순 키-값용)
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());

        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());

        return template;
    }

    // 2. 객체를 저장할 때 사용하는 템플릿 (Reservation 등)
    @Bean
    public RedisTemplate<String, Object> objectRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter
    ) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory); // Redis 서버와 연결 설정
        container.addMessageListener(listenerAdapter, new PatternTopic("__keyevent@0__:expired")); // 이벤트 리스너 등록
        return container;

    }

    @Bean
    public MessageListenerAdapter listenerAdapter(RedisExpiredEventListener listener) { // 커스텀 TTL 만료 감지 처리 클래스
        return new MessageListenerAdapter(listener); // Spring에서 Redis Pub/Sub 메시지를 받아서 클래스의 onMessage()로 연결해주는 어댑터
    }
}
