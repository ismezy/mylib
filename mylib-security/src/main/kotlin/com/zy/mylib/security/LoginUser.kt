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
   * Gets 用户ID.
   *
   * @return Value of 用户ID.
   */
  /**
   * Sets new 用户ID.
   *
   * @param userId New value of 用户ID.
   */
  /**
   * 用户ID
   */
  var userId: String? = null
  /**
   * Gets 用户中文名.
   *
   * @return Value of 用户中文名.
   */
  /**
   * Sets new 用户中文名.
   *
   * @param userName New value of 用户中文名.
   */
  /**
   * 用户中文名
   */
  var userName: String? = null
  /**
   * Gets 用户类型.
   *
   * @return Value of 用户类型.
   */
  /**
   * Sets new 用户类型.
   *
   * @param type New value of 用户类型.
   */
  /**
   * 用户类型
   */
  var type: String? = null
  /**
   * Gets 角色.
   *
   * @return Value of 角色.
   */
  /**
   * Sets new 角色.
   *
   * @param roles New value of 角色.
   */
  /**
   * 角色
   */
  var roles: Set<String>? = null
  /**
   * Gets 访问策略.
   *
   * @return Value of 访问策略.
   */
  /**
   * Sets new 访问策略.
   *
   * @param policy New value of 访问策略.
   */
  /**
   * 访问策略
   */
  var policy: Set<String>? = null
  /**
   * Gets 当前登录系统编号.
   *
   * @return Value of 当前登录系统编号.
   */
  /**
   * Sets new 当前登录系统编号.
   *
   * @param system New value of 当前登录系统编号.
   */
  /**
   * 当前登录系统编号
   */
  var system: String? = null
  /**
   * Gets 手机号码.
   *
   * @return Value of 手机号码.
   */
  /**
   * Sets new 手机号码.
   *
   * @param telephone New value of 手机号码.
   */
  /**
   * 手机号码
   */
  var telephone: String? = null
  /**
   * Gets 所属机构名称.
   *
   * @return Value of 所属机构名称.
   */
  /**
   * Sets new 所属机构名称.
   *
   * @param orgName New value of 所属机构名称.
   */
  /**
   * 所属机构名称
   */
  var orgName: String? = null
  /**
   * Gets 所属机构ID.
   *
   * @return Value of 所属机构ID.
   */
  /**
   * Sets new 所属机构ID.
   *
   * @param orgId New value of 所属机构ID.
   */
  /**
   * 所属机构ID
   */
  var orgId: String? = null
  /**
   * Gets 用户所属机构树路径.
   *
   * @return Value of 用户所属机构树路径.
   */
  /**
   * Sets new 用户所属机构树路径.
   *
   * @param orgPath New value of 用户所属机构树路径.
   */
  /**
   * 用户所属机构树路径
   */
  var orgPath: String? = null
  /**
   * Gets 登录IP.
   *
   * @return Value of 登录IP.
   */
  /**
   * Sets new 登录IP.
   *
   * @param ip New value of 登录IP.
   */
  /**
   * 登录IP
   */
  var ip: String? = null
  /**
   * Gets 最后登录时间.
   *
   * @return Value of 最后登录时间.
   */
  /**
   * Sets new 最后登录时间.
   *
   * @param lastLoginTime New value of 最后登录时间.
   */
  /**
   * 最后登录时间
   */
  var lastLoginTime: Date? = null
  /**
   * Gets 其他信息.
   *
   * @return Value of 其他信息.
   */
  /**
   * Sets new 其他信息.
   *
   * @param other New value of 其他信息.
   */
  /**
   * 其他信息
   */
  var other: Map<String, String>? = null
  override fun toString(): String {
    return userName + ":" + userId
  }

  companion object {
    private const val serialVersionUID = 1843206312339055362L
  }
}
