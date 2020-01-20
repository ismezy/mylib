package com.zy.mylib.cache.guava;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * Guava Cache Manager
 *
 * @author ASUS
 */
public class GuavaCacheManager implements CacheManager {
    private ConcurrentMap<String, GuavaCache> cacheMap = new ConcurrentHashMap<>();

    /**
     * 缓存有效期，默认30分钟
     */
    private long expire = 30;
    /**
     * 缓存有效期时间单位，默认分钟
     */
    private TimeUnit timeUnit = TimeUnit.MINUTES;

    public GuavaCacheManager() {
    }

    public GuavaCacheManager(long expire, TimeUnit timeUnit) {
        this.expire = expire;
        this.timeUnit = timeUnit;
    }

    @Override
    public Cache getCache(String name) {
        if (this.cacheMap.containsKey(name)) {
            return cacheMap.get(name);
        }
        return this.create(name, expire, timeUnit);
    }

    @Override
    public Collection<String> getCacheNames() {
        return cacheMap.keySet();
    }

    /**
     * 创建缓存
     *
     * @param name
     * @param expire
     * @param timeUnit
     */
    public GuavaCache create(String name, long expire, TimeUnit timeUnit) {
        GuavaCache cache = new GuavaCache(CacheBuilder.newBuilder().expireAfterAccess(expire, timeUnit).build());
        cacheMap.put(name, cache);
        return cache;
    }

    /**
     * Gets 缓存有效期时间单位，默认分钟.
     *
     * @return Value of 缓存有效期时间单位，默认分钟.
     */
    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    /**
     * Sets new 缓存有效期时间单位，默认分钟.
     *
     * @param timeUnit New value of 缓存有效期时间单位，默认分钟.
     */
    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    /**
     * Sets new 缓存有效期，默认30分钟.
     *
     * @param expire New value of 缓存有效期，默认30分钟.
     */
    public void setExpire(long expire) {
        this.expire = expire;
    }

    /**
     * Gets 缓存有效期，默认30分钟.
     *
     * @return Value of 缓存有效期，默认30分钟.
     */
    public long getExpire() {
        return expire;
    }
}
