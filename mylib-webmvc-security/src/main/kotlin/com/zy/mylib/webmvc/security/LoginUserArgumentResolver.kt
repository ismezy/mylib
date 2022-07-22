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
package com.zy.mylib.webmvc.security

import com.zy.mylib.security.LoginUser
import com.zy.mylib.security.Passport
import org.springframework.core.MethodParameter
import org.springframework.lang.Nullable
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

/**
 * @author ASUS
 */
class LoginUserArgumentResolver : HandlerMethodArgumentResolver {
  /**
   * Sets new passport.
   */
  var passport: Passport? = null
  override fun supportsParameter(parameter: MethodParameter): Boolean {
    return parameter.parameterType == LoginUser::class.java
  }

  @Throws(Exception::class)
  override fun resolveArgument(
    parameter: MethodParameter,
    @Nullable mavContainer: ModelAndViewContainer?,
    webRequest: NativeWebRequest,
    @Nullable binderFactory: WebDataBinderFactory?
  ): Any? {
    return passport!!.user
  }
}
