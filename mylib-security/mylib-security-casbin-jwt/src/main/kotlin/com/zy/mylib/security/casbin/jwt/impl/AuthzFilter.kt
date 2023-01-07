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

import com.zy.mylib.base.exception.BusException.Companion.builder
import com.zy.mylib.security.Passport
import org.casbin.jcasbin.main.Enforcer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpMethod
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletRequest

/**
 * @author ASUS
 */
class AuthzFilter : Filter {
  lateinit var enforcer: Enforcer

  lateinit var passport: Passport

  @Throws(IOException::class, ServletException::class)
  override fun doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain) {
    RequestContextHolder.setRequestAttributes(ServletRequestAttributes(req as HttpServletRequest))

    val request = req as HttpServletRequest
    if(HttpMethod.OPTIONS.matches(request.method)) {
      chain.doFilter(req, res)
      return
    }
    val isLogin = passport.isAuthenticated
    val user = passport.user

    val pass = enforcer.enforce(
        if (user == null || user.userId.isNullOrBlank()) "guest" else user.userId, request.requestURI, request.method)
    if (!pass) {
      throw builder().message(if (isLogin) "未授权" else "未登录").httpStatus(if (isLogin) 403 else 401).build()
    }
    chain.doFilter(req, res)
  }

  companion object {
    val log: Logger = LoggerFactory.getLogger(AuthzFilter::class.java)
  }
}
