package com.anzhen.config;

import com.anzhen.utils.FastJson2JsonRedisSerializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Classname : RedisConfig
 * @Date : 21/07/31 14:06
 * @Created : anzhen
 * @Description :
 * @目的:
 */
@Configuration
@EnableCaching
public class RedisConfig {
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        // 设置key的序列化
        template.setKeySerializer(new StringRedisSerializer());
        // 自定义值的序列化
        template.setValueSerializer(new FastJson2JsonRedisSerializer(Object.class));
        template.setDefaultSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
}
