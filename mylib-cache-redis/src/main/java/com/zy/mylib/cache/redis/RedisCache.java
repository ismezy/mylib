/*
 * Copyright © ${project.inceptionYear} ismezy (ismezy@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zy.mylib.cache.redis;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.data.redis.core.RedisOperations;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * redis cache impl
 *
 * @author ASUS
 */
public class RedisCache implements Cache {
    private String name;
    private long expire;
    private TimeUnit timeUnit;
    private RedisOperations<Object, Object> redisOperations;

    /**
     * new rediscache
     *
     * @param name
     * @param expire
     * @param timeUnit
     */
    public RedisCache(String name, RedisOperations<Object, Object> redisOperations, long expire, TimeUnit timeUnit) {
        this.name = name;
        this.expire = expire;
        this.timeUnit = timeUnit;
        this.redisOperations = redisOperations;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getNativeCache() {
        return redisOperations.opsForValue();
    }

    @Override
    public ValueWrapper get(Object key) {
        return new SimpleValueWrapper(this.get(key, Object.class));
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        redisOperations.expire(wrapperKey(key), this.expire, this.timeUnit);
        T value = (T) redisOperations.opsForValue().get(wrapperKey(key));
        return value;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        T value = (T) this.get(key, Object.class);
        if (value == null) {
            try {
                value = valueLoader.call();
                put(key, value);
            } catch (Exception e) {
                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                }
                e.printStackTrace();
            }
        }
        return value;
    }

    @Override
    public void put(Object key, Object value) {
        this.redisOperations.opsForValue().set(wrapperKey(key), value, this.expire, timeUnit);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        Object v = this.get(key, Object.class);
        if (v == null) {
            this.put(key, value);
            return null;
        }
        return new SimpleValueWrapper(v);
    }

    @Override
    public void evict(Object key) {
        redisOperations.delete(wrapperKey(key));
    }

    @Override
    public void clear() {
        redisOperations.delete(redisOperations.keys(this.name + ":*"));
    }

    protected String wrapperKey(Object key) {
        return this.name + ":" + key;
    }
}
