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
package com.zy.mylib.security.casbin.jwt.impl

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.zy.mylib.base.exception.BusException.Companion.builder
import com.zy.mylib.cache.redis.RedisUserCache
import com.zy.mylib.security.LoginUser
import com.zy.mylib.security.Passport
import com.zy.mylib.utils.StringUtils
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisOperations
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.io.Serializable
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named
import javax.servlet.http.HttpServletRequest

/**
 * @author ASUS
 */
@Named
class RedisJWTPassportImpl : Passport<LoginUser>, InitializingBean {
  /**
   * Gets cache.
   *
   * @return Value of cache.
   */
  /**
   * Sets new cache.
   *
   * @param userCache New value of cache.
   */
  var userCache: RedisUserCache? = null

  /**
   * 登录重试次数,默认5次
   */
  @Value("\${mylib.auth.retry:5}")
  private val retry: Int? = null

  /**
   * 拒绝登录时间，分钟(默认10分钟)
   */
  @Value("\${mylib.auth.rejectMin:10}")
  private val rejectMin: Int? = null

  @Inject
  @Named("jwtTokenRedisOperations")
  private var redisOperations: RedisOperations<String, Serializable>? = null
  /**
   * Gets algorithm.
   *
   * @return Value of algorithm.
   */
  /**
   * Sets new algorithm.
   *
   * @param algorithm New value of algorithm.
   */
  var algorithm = Algorithm.HMAC256(Passport.HMAC_SECRET)

  //
  //    public RedisJWTPassportImpl(RedisOperations<String, ? extends Serializable> redisOperations) {
  //        this.redisOperations = redisOperations;
  //    }
  val request: HttpServletRequest
    get() {
      val attrs = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
        ?: throw builder().message("当前线程中不存在 Request 上下文").build()
      return attrs.request
    }
  override val token: String
    get() {
      val request = request
      val headerToken = request.getHeader(HEADER_TOKEN_KEY)
      return headerToken ?: request.getParameter(QUERY_TOKEN_KEY)
    }
  override val user: LoginUser
    get() {
      val token = token
      return if (StringUtils.isBlank(token)) LoginUser() else userCache!!.get<LoginUser>(token, "user")
    }
  override val isAuthenticated: Boolean
    get() {
      val token = token
      if (StringUtils.isBlank(token)) {
        return false
      }
      val b = userCache!!.get<Boolean>(token, "isAuthenticated")
      return b ?: false
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
      .withArrayClaim("roles", user.roles!!.toTypedArray())
      .withIssuedAt(now) // 1天有效
      .withExpiresAt(Date(now.time + 1000 * 60 * 60 * 24))
      .sign(algorithm)
    val request = request
    var remoteAddr = request.getHeader("X-FORWARDED-FOR")
    if (remoteAddr == null || "" == remoteAddr) {
      remoteAddr = request.remoteAddr
    }
    user.ip = remoteAddr
    userCache!!.put(token, "user", user)
    userCache!!.put(token, "isAuthenticated", true)
    return token
  }

  override fun onLoginFailed(user: String?) {
    val request = request
    var remoteAddr = request.getHeader("X-FORWARDED-FOR")
    if (StringUtils.isBlank(remoteAddr)) {
      remoteAddr = request.remoteAddr
    }
    val key = "login:failed:$remoteAddr:$user"
    var count = redisOperations!!.opsForValue()[key] as Int
    if (count == null) {
      count = 1
      redisOperations!!.opsForValue()[key, count, rejectMin!!.toLong()] = TimeUnit.MINUTES
    } else {
      redisOperations!!.opsForValue()[key] = count + 1
      redisOperations!!.expire(key, rejectMin!!.toLong(), TimeUnit.MINUTES)
    }
  }

  override fun isLock(user: String?): Boolean {
    val request = request
    var remoteAddr = request.getHeader("X-FORWARDED-FOR")
    if (StringUtils.isBlank(remoteAddr)) {
      remoteAddr = request.remoteAddr
    }
    val key = "login:failed:$remoteAddr:$user"
    val count = redisOperations!!.opsForValue()[key] as Int
    return if (count != null && count >= retry!!) {
      true
    } else false
  }

  /**
   * {@inheritDoc}
   */
  override fun update(user: LoginUser) {
    userCache!!.put(token, "user", user)
  }

  override fun logout() {
    userCache!!.removeToken(token)
  }

  override val privoder: String
    get() = "local"

  @Throws(Exception::class)
  override fun afterPropertiesSet() {
    userCache = RedisUserCache(redisOperations!!, 1, TimeUnit.DAYS)
  }

  /**
   * Gets redisOperations.
   *
   * @return Value of redisOperations.
   */
  fun getRedisOperations(): RedisOperations<String, out Serializable>? {
    return redisOperations
  }

  /**
   * Sets new redisOperations.
   *
   * @param redisOperations New value of redisOperations.
   */
  fun setRedisOperations(redisOperations: RedisOperations<String, Serializable>?) {
    this.redisOperations = redisOperations
  }

  companion object {
    const val HEADER_TOKEN_KEY = "token"
    const val QUERY_TOKEN_KEY = "__token"
  }
}
