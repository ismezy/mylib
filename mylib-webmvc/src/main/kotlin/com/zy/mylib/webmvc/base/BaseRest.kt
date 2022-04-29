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
package com.zy.mylib.webmvc.base

import com.zy.mylib.base.exception.BusException
import com.zy.mylib.base.i18n.I18n
import com.zy.mylib.base.i18n.LocalMessage
import com.zy.mylib.webmvc.model.RestMessage
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.context.request.WebRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author ASUS
 */
open class BaseRest : I18n() {
  protected var logger = LoggerFactory.getLogger(BaseRest::class.java)

  @Autowired(required = false)
  private val message: LocalMessage? = null

  @Value("\${mylib.exception.defaultMessage:请求失败}")
  private val defaultMessage: String? = null
  @ExceptionHandler(Exception::class)
  @ResponseBody
  @Throws(Exception::class)
  fun handleUncaughtException(ex: Exception?, request: WebRequest?, response: HttpServletResponse?): RestMessage? {
    val result = RestMessage("501", ex?.message)
    response?.status = 500
    if (ex is BusException) {
      logger.warn(ex.message)
      val bex = ex as BusException?
      response?.status = bex!!.httpStatus
      result.code = bex.code
    } else if (ex is MethodArgumentNotValidException) {
      val vex = ex as MethodArgumentNotValidException?
      val errors = vex!!.bindingResult.allErrors
      var message = ""
      for (err in errors) {
        message += err.defaultMessage + "！"
      }
      logger.warn(message)
      result.message = message
    } else if (ex is BindException) {
      val bex = ex as BindException?
      val errors = bex!!.bindingResult.allErrors
      var message = """
            验证错误，共有${errors.size}个错误：<br/>
            
            """.trimIndent()
      for (err in errors) {
        message += """
                ${err.defaultMessage}<br/>
                
                """.trimIndent()
      }
      logger.warn(message)
      result.message = message
    } else {
      if (ex != null) {
        logger.error(ex.message, ex)
      }
      result.message = defaultMessage
    }
    return result
  }
}
