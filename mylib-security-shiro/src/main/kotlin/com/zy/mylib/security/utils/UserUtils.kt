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
package com.zy.mylib.security.utils

import com.zy.mylib.security.service.UserService
import org.apache.shiro.SecurityUtils
import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component
import java.util.*

/**
 * 用户工具类
 *
 * @author ASUS
 */
@Component("securityUserUtils")
class UserUtils : ApplicationContextAware {
  @Throws(BeansException::class)
  override fun setApplicationContext(applicationContext: ApplicationContext) {
    context = applicationContext
  }

  companion object {
    protected var context: ApplicationContext? = null

    /**
     * 获取当前用户
     *
     * @return
     */
    fun <T> getCurrentUser(): T? {
      if (SecurityUtils.getSubject().principal is String) {
        val userManager = context!!.getBean(UserService::class.java) as UserService<T>
        val user: Optional<T>? = userManager.findById(SecurityUtils.getSubject().principal as String)
        return user!!.get()
      }
      return SecurityUtils.getSubject().principal as T
    }

    val isLogin: Boolean
      get() = SecurityUtils.getSubject().isAuthenticated
  }
}
