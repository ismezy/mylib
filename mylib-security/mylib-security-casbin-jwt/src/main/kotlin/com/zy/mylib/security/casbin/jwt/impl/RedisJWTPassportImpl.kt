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
package com.zy.mylib.security.casbin.jwt.impl

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.zy.mylib.cache.redis.RedisUserCache
import com.zy.mylib.security.AbstractPassport
import com.zy.mylib.security.LoginUser
import com.zy.mylib.security.Passport
import com.zy.mylib.utils.StringUtils
import org.springframework.beans.factory.InitializingBean
import org.springframework.data.redis.core.RedisOperations
import java.io.Serializable
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

/**
 * @author ASUS
 */
@Named
class RedisJWTPassportImpl : AbstractPassport(), InitializingBean {

  lateinit var userCache: RedisUserCache



  @Inject
  @Named("jwtTokenRedisOperations")
  lateinit var redisOperations: RedisOperations<String, Serializable>

  var algorithm: Algorithm = Algorithm.HMAC256(Passport.HMAC_SECRET)



  override val user: LoginUser?
    get() {
      return if (token.isNullOrBlank()) LoginUser() else userCache[token!!, "user"]
    }
  override val isAuthenticated: Boolean
    get() {
      return if(token.isNullOrBlank()) {
        false
      } else {
        userCache[token!!, "isAuthenticated"] ?: false
      }
    }

  override fun login(user: LoginUser): String? {
    val now = Date()
    val token = JWT.create().withSubject(user.userId)
      .withClaim("phone", user.telephone)
      .withClaim("name", user.userName)
      .withClaim("type", user.type)
      .withClaim("orgId", user.orgId)
      .withClaim("orgName", user.orgName)
      .withClaim("orgPath", user.orgPath)
      .withClaim("system", user.system)
      .withClaim("loginUser", user.userId)
      .withArrayClaim("roles", user.roles?.toTypedArray())
      .withIssuedAt(now) // 1天有效
      .withExpiresAt(Date(now.time + 1000 * 60 * 60 * 24))
      .sign(algorithm)
    val request = request
    var remoteAddr = request.getHeader("X-FORWARDED-FOR")
    if (remoteAddr == null || "" == remoteAddr) {
      remoteAddr = request.remoteAddr
    }
    user.ip = remoteAddr
    userCache.put(token, "user", user)
    userCache.put(token, "isAuthenticated", true)
    return token
  }

  override fun onLoginFailed(user: String?) {
    var remoteAddr = request.getHeader("X-FORWARDED-FOR")
    if (StringUtils.isBlank(remoteAddr)) {
      remoteAddr = request.remoteAddr
    }
    val key = "login:failed:$remoteAddr:$user"
    val count = redisOperations.opsForValue()[key] as Int
    redisOperations.opsForValue()[key] = count + 1
    redisOperations.expire(key, rejectMin.toLong(), TimeUnit.MINUTES)
  }

  override fun isLock(user: String?): Boolean {
    val request = request
    var remoteAddr = request.getHeader("X-FORWARDED-FOR")
    if (StringUtils.isBlank(remoteAddr)) {
      remoteAddr = request.remoteAddr
    }
    val key = "login:failed:$remoteAddr:$user"
    val count = redisOperations.opsForValue()[key] as Int
    return count >= retry
  }

  /**
   * {@inheritDoc}
   */
  override fun update(user: LoginUser) {
    token?.let { userCache.put(it, "user", user) }
  }

  override fun logout() {
    token?.let { userCache.removeToken(it) }
  }

  override val provider: String
    get() = "local"

  @Throws(Exception::class)
  override fun afterPropertiesSet() {
    userCache = RedisUserCache(redisOperations, 1, TimeUnit.DAYS)
  }

}
