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

import org.springframework.data.redis.core.RedisOperations
import org.springframework.cache.support.SimpleValueWrapper
import java.io.Serializable
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit

/**
 * @author ASUS
 */
class RedisUserCache
/**
 * new rediscache
 */(private val redisOperations: RedisOperations<String, out Serializable>, private val expire: Long, private val timeUnit: TimeUnit) {
  operator fun <T : Serializable?> get(token: String, key: String?): T {
    updateTTL(token)
    return redisOperations.opsForHash<Any, Any>()[wrapperToken(token), key] as T
  }

  fun <T : Serializable?> put(token: String, key: String, value: T) {
    updateTTL(token)
    redisOperations.opsForHash<Any, Any>().put(wrapperToken(token), key, value)
  }

  fun <T : Serializable?> put(token: String, map: Map<String, T>?) {
    updateTTL(token)
    redisOperations.opsForHash<Any, Any>().putAll(wrapperToken(token), map)
  }

  fun removeToken(token: String) {
    redisOperations.delete(wrapperToken(token))
  }

  private fun updateTTL(token: String) {
    redisOperations.expire(wrapperToken(token), expire, timeUnit)
  }

  fun wrapperToken(token: String): String {
    return "userToken:$token"
  }
}