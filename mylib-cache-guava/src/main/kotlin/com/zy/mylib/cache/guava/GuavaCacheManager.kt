/*
 * Copyright © 2020 ismezy (ismezy@qq.com)
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
package com.zy.mylib.cache.guava

import com.google.common.cache.CacheBuilder
import com.zy.mylib.cache.guava.GuavaCache
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.cache.support.SimpleValueWrapper
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

/**
 * Guava Cache Manager
 *
 * @author ASUS
 */
class GuavaCacheManager() : CacheManager {
  private val cacheMap: ConcurrentMap<String, GuavaCache> = ConcurrentHashMap()
  /**
   * 缓存有效期，默认30分钟
   */
  var expire: Long = 30
  /**
   * 缓存有效期时间单位，默认分钟
   */
  var timeUnit = TimeUnit.MINUTES

  constructor(expire: Long, timeUnit: TimeUnit) : this() {
    this.expire = expire
    this.timeUnit = timeUnit
  }

  override fun getCache(name: String): Cache {
    return if (cacheMap.containsKey(name)) {
      cacheMap[name]!!
    } else create(name, expire, timeUnit)
  }

  override fun getCacheNames(): Collection<String> {
    return cacheMap.keys
  }

  /**
   * 创建缓存
   *
   * @param name
   * @param expire
   * @param timeUnit
   */
  fun create(name: String, expire: Long, timeUnit: TimeUnit?): GuavaCache {
    val cache = GuavaCache(CacheBuilder.newBuilder().expireAfterAccess(expire, timeUnit).build())
    cacheMap[name] = cache
    return cache
  }
}