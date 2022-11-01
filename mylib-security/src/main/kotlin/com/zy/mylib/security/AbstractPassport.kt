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