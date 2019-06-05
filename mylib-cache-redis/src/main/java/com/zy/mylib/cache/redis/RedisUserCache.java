package com.zy.mylib.cache.redis;

import org.springframework.data.redis.core.RedisOperations;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ASUS
 */
public class RedisUserCache {
     private long expire;
     private TimeUnit timeUnit;
     private RedisOperations<String,  ? extends Serializable> redisOperations;

     /**
     * new rediscache
     */
    public RedisUserCache(RedisOperations<String,  ? extends Serializable> redisOperations, long expire, TimeUnit timeUnit) {
        this.expire = expire;
        this.timeUnit = timeUnit;
        this.redisOperations = redisOperations;
    }

    public <T extends Serializable> T get(String token, String key) {
        updateTTL(token);
        return (T) redisOperations.opsForHash().get(wrapperToken(token), key);
    }

    public <T extends Serializable> void put(String token, String key, T value) {
        updateTTL(token);
        redisOperations.opsForHash().put(wrapperToken(token), key, value);
    }

    public <T extends Serializable> void put(String token, Map<String, T> map) {
        updateTTL(token);
        redisOperations.opsForHash().putAll(wrapperToken(token), map);
    }

    public void removeToken(String token) {
        redisOperations.delete(wrapperToken(token));
    }

    private void updateTTL(String token) {
        redisOperations.expire(wrapperToken(token), expire, timeUnit);
    }

    String wrapperToken(String token) {
        return "userToken:" + token;
    }
}
