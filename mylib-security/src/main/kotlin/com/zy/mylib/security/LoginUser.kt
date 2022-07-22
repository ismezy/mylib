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
import java.util.*

/**
 * OAuth2 用户
 *
 * @author ASUS
 */
class LoginUser : Serializable {
  /**
   * 用户ID
   */
  var userId: String? = null

  /**
   * 用户中文名
   */
  var userName: String? = null

  /**
   * 用户类型(provider)
   */
  var type: String? = null

  /**
   * 角色
   */
  var roles: Set<String>? = null

  /**
   * 当前登录系统编号
   */
  var system: String? = null

  /**
   * 手机号码
   */
  var telephone: String? = null

  /**
   * 所属机构名称
   */
  var orgName: String? = null

  /**
   * 所属机构ID
   */
  var orgId: String? = null

  /**
   * 用户所属机构树路径
   */
  var orgPath: String? = null

  /**
   * 登录IP
   */
  var ip: String? = null

  /**
   * 最后登录时间
   */
  var lastLoginTime: Date? = null

  /**
   * 其他信息
   */
  var other: Map<String, String>? = null

  /**
   * 原用户对象
   */
  var user: Any? = null

  fun <T> getOriginUser(): T? {
    return user as T?
  }


  override fun toString(): String {
    return "$userName:$userId"
  }

  companion object {
    private const val serialVersionUID = 1843206312339055362L
  }
}
