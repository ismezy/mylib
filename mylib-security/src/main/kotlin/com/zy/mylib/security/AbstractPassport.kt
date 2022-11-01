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
package com.zy.mylib.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletRequest

/**
 * passport基础实现
 */
open abstract class AbstractPassport : Passport {
  /**
   * 登录重试次数,默认5次
   */
  @Value("\${mylib.auth.retry:5}")
  var retry: Int = 5

  /**
   * 拒绝登录时间，分钟(默认10分钟)
   */
  @Value("\${mylib.auth.rejectMin:10}")
  var rejectMin = 10

  protected val request: HttpServletRequest
    get() {
      val attrs = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
      return attrs.request
    }

  override val token: String?
    get() {
      val request = request
      val headerToken = request.getHeader(Passport.HEADER_TOKEN_KEY)
      return headerToken ?: request.getParameter(Passport.QUERY_TOKEN_KEY)
    }
}