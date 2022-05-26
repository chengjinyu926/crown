package com.cjy.crown.sms.config;

import com.cjy.crown.sms.core.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/9 21:15
 * @description：
 */
@Component
public class RedisConfig {
    @Bean
    @SuppressWarnings(value = {"unchecked","rawtypes"})
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<Object,Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        FastJsonRedisSerializer serializer = new FastJsonRedisSerializer(Object.class);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();
        return template;
    }
}
