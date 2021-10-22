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
package com.zy.mylib.base.i18n

import org.springframework.beans.factory.annotation.Autowired
import com.zy.mylib.base.exception.BusException
import java.util.regex.Pattern

open class I18n {
  @Autowired(required = false)
  private val message: LocalMessage? = null
  protected fun getMessage(code: String?, vararg args: Any?): String? {
    return message!!.getMessage(code, *args)
  }

  fun throwBusException(code: String, messagePrefix: String): BusException {
    return BusException(getMessage(messagePrefix + code), code)
  }

  /**
   * 返回异常
   *
   * @param messageTemplate 消息模板
   * @param args            模板参数
   * @return
   */
  fun throwBusExcption(messageTemplate: String?, vararg args: Any?): BusException {
    val m = messageCodePattern.matcher(messageTemplate)
    m.find()
    val code = m.group(1)
    return BusException(getMessage(messageTemplate, *args), code)
  }

  /**
   * 返回异常
   *
   * @param messageTemplate 消息模板
   * @param args            模板参数
   * @return
   */
  fun newBusException(messageTemplate: String?, vararg args: Any?): BusException {
    val m = messageCodePattern.matcher(messageTemplate)
    m.find()
    val code = m.group(1)
    return BusException(getMessage(messageTemplate, *args), code)
  }

  /**
   * 返回异常
   *
   * @param messageTemplate 消息模板
   * @param args            模板参数
   * @return
   */
  fun newBusException(httpStatus: Int, messageTemplate: String?, vararg args: Any?): BusException {
    val m = messageCodePattern.matcher(messageTemplate)
    m.find()
    val code = m.group(1)
    return BusException(getMessage(messageTemplate, *args), code, httpStatus)
  }

  companion object {
    val messageCodePattern = Pattern.compile("^.*(\\d+)$")
  }
}