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
package com.zy.mylib.base.exception

import java.lang.RuntimeException

/**
 * @author ASUS
 */
class BusException : RuntimeException {
  var code = SUCCESS_CODE
  /**
   * Gets httpStatus.
   *
   * @return Value of httpStatus.
   */
  /**
   * Sets new httpStatus.
   *
   * @param httpStatus New value of httpStatus.
   */
  var httpStatus = 500

  constructor(message: String?) : super(message) {
    this.code = ERROR_CODE
  }

  constructor(cause: Throwable?) : super(cause) {
    this.code = ERROR_CODE
  }

  constructor(message: String?, cause: Throwable?) : super(message, cause) {
    this.code = ERROR_CODE
  }

  constructor(message: String?, code: String) : super(message) {
    this.code = code
  }

  constructor(message: String?, code: String, cause: Throwable?) : super(message, cause) {
    this.code = code
  }

  constructor(message: String?, code: String, httpStatus: Int) : super(message) {
    this.code = code
    this.httpStatus = httpStatus
  }

  class BusExceptionBuilder {
    private var message: String? = null
    private var code = "9999"
    private var httpStatus = 500
    fun message(message: String?): BusExceptionBuilder {
      this.message = message
      return this
    }

    fun code(code: String): BusExceptionBuilder {
      this.code = code
      return this
    }

    fun httpStatus(status: Int): BusExceptionBuilder {
      httpStatus = status
      return this
    }

    /**
     * 生成
     *
     * @return
     */
    fun build(): BusException {
      return BusException(message, code, httpStatus)
    }
  }

  companion object {
    const val ERROR_CODE = "300"
    const val SUCCESS_CODE = "0000"

    /**
     *
     */
    private const val serialVersionUID = 5058689238804568643L

    @JvmStatic
    fun builder(): BusExceptionBuilder {
      return BusExceptionBuilder()
    }
  }
}