package org.jasonxiao.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.DefaultRedisCachePrefix;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCachePrefix;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Jason Xiao
 */
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager(RedisTemplate<String, Object> rabbitTemplate) {
        RedisCacheManager redisCacheManager = new RedisCacheManager(rabbitTemplate);
        redisCacheManager.setTransactionAware(true);
        redisCacheManager.setDefaultExpiration(60);
//        RedisCachePrefix cachePrefix = new DefaultRedisCachePrefix(".");
//        cachePrefix.prefix("capstone");
//        redisCacheManager.setCachePrefix(cachePrefix);
        return redisCacheManager;
    }
}
