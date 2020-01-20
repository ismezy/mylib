package com.zy.mylib.cache.redis;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisOperations;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @author ASUS
 */
public class RedisCacheManager implements CacheManager {
    private RedisOperations<Object, Object> redisOperations;
    private long expire;
    private TimeUnit timeUnit;

    public RedisCacheManager(RedisOperations<Object, Object> redisOperations, long expire, TimeUnit timeUnit) {
        this.redisOperations = redisOperations;
        this.expire = expire;
        this.timeUnit = timeUnit;

    }

    @Override
    public Cache getCache(String name) {
        return new RedisCache(name, redisOperations, expire, timeUnit);
    }

    @Override
    public Collection<String> getCacheNames() {
        return null;
    }
}
