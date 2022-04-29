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
package com.zy.mylib.security.casbin.zuul.filter

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import com.netflix.zuul.exception.ZuulException
import com.zy.mylib.base.exception.BusException.Companion.builder
import com.zy.mylib.security.Passport
import com.zy.mylib.security.casbin.EnforcerManager
import com.zy.mylib.utils.StringUtils

/**
 * zuul 授权控制filter
 *
 * @author ASUS
 */
class ZuulAuthzFilter : ZuulFilter() {
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
  var enforcerManager: EnforcerManager<String>? = null
  /**
   * Gets 启用.
   *
   * @return Value of 启用.
   */
  /**
   * Sets new 启用.
   *
   * @param enabled New value of 启用.
   */
  /**
   * 启用
   */
  var isEnabled = true
  private val algorithm = Algorithm.HMAC256(Passport.HMAC_SECRET)
  override fun filterType(): String {
    return "pre"
  }

  override fun filterOrder(): Int {
    return 0
  }

  override fun shouldFilter(): Boolean {
    return isEnabled
  }

  @Throws(ZuulException::class)
  override fun run(): Any? {
//        Model model = new Model();
//        try {
//            model.loadModelFromText(FileUtils.readAllText(modelStream));
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new ZuulException(e, "读取model失败", 500, e.getCause().toString());
//        }
    val ctx = RequestContext.getCurrentContext()
    val request = ctx.request
    val token = request.getHeader("token")
    var user: String = "anon"
    var isLogin = false
    if (!StringUtils.isBlank(token)) {
      try {
        val jwt = JWT.require(algorithm).build().verify(token)
        user = jwt.subject
        isLogin = true
      } catch (e: JWTVerificationException) {
        e.printStackTrace()
      }
    }
    val enforcer = enforcerManager!!.getEnforcer(user)
    enforcer!!.enableLog(true)
    val pass = enforcer.enforce(user, request.requestURI, request.method)
    if (!pass) {
      throw builder().message(if (isLogin) "未授权" else "未登录").httpStatus(if (isLogin) 403 else 401).build()
    }
    return null
  }
}
