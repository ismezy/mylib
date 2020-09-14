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
