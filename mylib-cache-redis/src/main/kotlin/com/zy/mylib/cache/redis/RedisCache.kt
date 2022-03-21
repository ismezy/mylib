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
package com.zy.mylib.cache.redis

import org.springframework.cache.Cache
import org.springframework.data.redis.core.RedisOperations
import org.springframework.cache.support.SimpleValueWrapper
import org.springframework.lang.Nullable
import java.lang.Exception
import java.lang.RuntimeException
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

/**
 * redis cache impl
 *
 * @author ASUS
 */
class RedisCache
/**
 * new rediscache
 *
 * @param name
 * @param expire
 * @param timeUnit
 */(private val name: String, private val redisOperations: RedisOperations<String, Any>, private val expire: Long, private val timeUnit: TimeUnit) : Cache {
  override fun getName(): String {
    return name
  }

  override fun getNativeCache(): Any {
    return redisOperations.opsForValue()
  }

  override fun get(key: Any): Cache.ValueWrapper {
    return SimpleValueWrapper(this.get(key, Any::class.java))
  }

  override fun <T : Any?> get(key: Any, @Nullable type: Class<T>?): T? {
    redisOperations.expire(wrapperKey(key), expire, timeUnit)
    return redisOperations.opsForValue()[wrapperKey(key)] as T
  }

  override fun <T> get(key: Any, valueLoader: Callable<T>): T {
    var value: T = this.get(key, Any::class.java) as T
    if (value == null) {
      try {
        value = valueLoader.call()
        put(key, value!!)
      } catch (e: Exception) {
        if (e is RuntimeException) {
          throw e
        }
        e.printStackTrace()
      }
    }
    return value
  }

  override fun put(key: Any, value: Any) {
    redisOperations.opsForValue()[wrapperKey(key), value, expire] = timeUnit
  }

  override fun putIfAbsent(key: Any, value: Any): Cache.ValueWrapper {
    val v = this.get(key, Any::class.java)
    if (v == null) {
      put(key, value)
      return SimpleValueWrapper(null)
    }
    return SimpleValueWrapper(v)
  }

  override fun evict(key: Any) {
    redisOperations.delete(wrapperKey(key))
  }

  override fun clear() {
    redisOperations.delete(redisOperations.keys("$name:*"))
  }

  protected fun wrapperKey(key: Any): String {
    return name + ":" + key
  }
}
