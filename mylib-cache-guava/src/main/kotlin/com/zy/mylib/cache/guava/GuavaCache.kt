/**
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
package com.zy.mylib.cache.guava

import com.google.common.cache.Cache
import com.zy.mylib.cache.guava.GuavaCache
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cache.support.SimpleValueWrapper
import java.util.concurrent.Callable
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ExecutionException

/**
 * Guava cache
 *
 * @author ASUS
 */
class GuavaCache(private val guavaCache: Cache<Any, Any>) : org.springframework.cache.Cache {
  private var logger: Logger = LoggerFactory.getLogger(GuavaCache::class.java)
  private val name: String? = null
  override fun getName(): String {
    return name!!
  }

  override fun getNativeCache(): Any {
    return guavaCache
  }

  override fun get(key: Any): org.springframework.cache.Cache.ValueWrapper {
    val value = guavaCache.getIfPresent(key)
    return SimpleValueWrapper(value)
  }

  override fun <T> get(key: Any, type: Class<T>): T {
    return guavaCache.getIfPresent(key) as T
  }

  override fun <T> get(key: Any, valueLoader: Callable<T>): T? {
    return try {
      guavaCache[key, valueLoader] as T
    } catch (e: ExecutionException) {
      logger.warn("获取缓存异常，%s", e.message)
      logger.debug("获取缓存异常", e)
      null
    }
  }

  override fun put(key: Any, value: Any) {
    guavaCache.put(key, value)
  }

  override fun putIfAbsent(key: Any, value: Any): org.springframework.cache.Cache.ValueWrapper {
    return this[key]
  }

  override fun evict(key: Any) {
    guavaCache.invalidate(key)
  }

  override fun clear() {
    guavaCache.invalidateAll()
  }
}