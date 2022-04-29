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

import com.auth0.jwt.algorithms.Algorithm
import com.zy.mylib.base.exception.BusException.Companion.builder
import com.zy.mylib.security.LoginUser
import com.zy.mylib.security.Passport
import com.zy.mylib.security.casbin.EnforcerManager
import com.zy.mylib.utils.StringUtils
import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletRequest

/**
 * @author ASUS
 */
class AuthzFilter : Filter {
  /**
   * Gets enforcer manager.
   *
   * @return Value of enforcer manager.
   */
  /**
   * Sets new enforcer manager.
   *
   * @param enforcerManager New value of enforcer manager.
   */
  /**
   * enforcer manager
   */
  var enforcerManager: EnforcerManager<LoginUser>? = null
  /**
   * Gets jwt token hash function.
   *
   * @return Value of jwt token hash function.
   */
  /**
   * Sets new jwt token hash function.
   *
   * @param algorithm New value of jwt token hash function.
   */
  /**
   * jwt token hash function
   */
  var algorithm = Algorithm.HMAC256(Passport.HMAC_SECRET)
  /**
   * Gets passport.
   *
   * @return Value of passport.
   */
  /**
   * Sets new passport.
   *
   * @param passport New value of passport.
   */
  /**
   * passport
   */
  var passport: Passport<out LoginUser>? = null
  @Throws(IOException::class, ServletException::class)
  override fun doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain) {
    val isLogin = passport!!.isAuthenticated
    val user = passport!!.user
    val enforcer = enforcerManager!!.getEnforcer(user)
    if (isLogin) {
      enforcer.addRoleForUser(user?.userId, "user")
    }
    val request = req as HttpServletRequest
    val pass = enforcer!!.enforce(
      if (StringUtils.isBlank(user.userId)) "guest" else user.userId,
      request.requestURI, request.method
    )
    if (!pass) {
      throw builder().message(if (isLogin) "未授权" else "未登录").httpStatus(if (isLogin) 403 else 401).build()
    }
    chain.doFilter(req, res)
  }
}
