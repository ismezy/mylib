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

import java.io.Serializable

/**
 * @author ASUS
 */
interface Passport<T : Serializable?> {
  /**
   * 获取当前登录用户
   * @return
   */
  val user: T

  /**
   * 是否已登录
   * @return
   */
  val isAuthenticated: Boolean

  /**
   * 获取token
   * @return
   */
  val token: String?

  /**
   * 登录，返回token
   *
   * @param user
   * @return
   */
  fun login(user: T): String?

  /**
   * 登录失败
   * @param user
   */
  fun onLoginFailed(user: String?)

  /**
   * 用户是否已锁定
   * @param user
   * @return
   */
  fun isLock(user: String?): Boolean

  /**
   * 更新登录用户缓存
   * @param user
   */
  fun update(user: T)

  /**
   * 登出
   */
  fun logout()

  /**
   * passport 类型
   *
   * @return
   */
  val privoder: String?

  companion object {
    const val HMAC_SECRET = "com.mylib"
  }
}
