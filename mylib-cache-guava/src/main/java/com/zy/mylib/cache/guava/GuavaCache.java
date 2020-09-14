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
package com.zy.mylib.cache.guava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * Guava cache
 *
 * @author ASUS
 */
public class GuavaCache implements Cache {
    Logger logger = LoggerFactory.getLogger(GuavaCache.class);

    private com.google.common.cache.Cache<Object, Object> guavaCache;
    private String name;

    public GuavaCache(com.google.common.cache.Cache<Object, Object> cache) {
        this.guavaCache = cache;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getNativeCache() {
        return guavaCache;
    }

    @Override
    public ValueWrapper get(Object key) {
        Object value = guavaCache.getIfPresent(key);
        return new SimpleValueWrapper(value);
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        return (T) guavaCache.getIfPresent(key);
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        try {
            return (T) guavaCache.get(key, valueLoader);
        } catch (ExecutionException e) {
            logger.warn("获取缓存异常，%s", e.getMessage());
            logger.debug("获取缓存异常", e);
            return null;
        }
    }

    @Override
    public void put(Object key, Object value) {
        guavaCache.put(key, value);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        return this.get(key);
    }

    @Override
    public void evict(Object key) {
        guavaCache.invalidate(key);
    }

    @Override
    public void clear() {
        guavaCache.invalidateAll();
    }
}
