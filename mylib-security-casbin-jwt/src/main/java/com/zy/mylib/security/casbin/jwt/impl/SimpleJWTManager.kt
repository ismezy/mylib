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
package com.zy.mylib.security.casbin.jwt.impl

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.zy.mylib.security.casbin.jwt.JWTManager

/**
 * jwt manager最简实现
 *
 * @author ASUS
 */
class SimpleJWTManager : JWTManager {
  /**
   * Gets jwt 加密算法.
   *
   * @return Value of jwt 加密算法.
   */
  /**
   * Sets new jwt 加密算法.
   *
   * @param algorithm New value of jwt 加密算法.
   */
  /**
   * jwt 加密算法
   */
  var algorithm: Algorithm? = null
  override fun decode(tokenString: String?): DecodedJWT? {
    return JWT.require(algorithm).build().verify(tokenString)
  }

  override fun sign(user: String?, phone: String?, name: String?, role: String?): String? {
    return JWT.create().withSubject(user)
      .withClaim("name", name)
      .withClaim("phone", phone)
      .withClaim("role", role)
      .sign(algorithm)
  }
}
