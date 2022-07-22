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
package com.zy.mylib.security.provider

import com.zy.mylib.base.exception.BusException
import com.zy.mylib.security.LoginUser
import com.zy.mylib.security.OAuthUserVerifier
import com.zy.mylib.security.manager.UserManager
import com.zy.mylib.utils.HashUtils
import org.springframework.stereotype.Component
import javax.inject.Inject

@Component
class UserPasswordOAuthUserVerifier: OAuthUserVerifier<UserPasswordOAuthUser> {
  @Inject
  lateinit var userManager: UserManager

  override fun verify(oauthUser: UserPasswordOAuthUser): LoginUser {
    val pwd = HashUtils.getMd5(oauthUser.id  + oauthUser.password + userManager.salt)
    val user = userManager.findOne("loginId", oauthUser.id!!)
    if(user?.password == pwd) {
      return LoginUser().apply {
        this.userName = user?.username
        this.userId = user?.loginId
        this.user = user
        this.type = "default"
      }
    }
    throw BusException("用户不存在或密码错误")
  }

}