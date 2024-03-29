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
package com.zy.mylib.app.admin.controller

import com.zy.mylib.security.OAuthUserVerifier
import com.zy.mylib.security.Passport
import com.zy.mylib.security.dto.LoginResponse
import com.zy.mylib.security.dto.PwdLoginRequest
import com.zy.mylib.security.provider.UserPasswordOAuthUser
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.inject.Inject

@RestController
@RequestMapping
class LoginController {
  @Inject
  lateinit var passport: Passport

  @Inject
  lateinit var userVerifier: OAuthUserVerifier<UserPasswordOAuthUser>

  @GetMapping("/login")
  fun loginPage(): String {
    return "login"
  }

  @PostMapping("/login")
  fun login(@Validated @RequestBody req: PwdLoginRequest): LoginResponse {
    val loginUser = userVerifier.verify(UserPasswordOAuthUser().apply {
      this.id = req.loginId
      this.password = req.password
    })
    val token = passport.login(loginUser)
    return LoginResponse().apply {
      this.token = token!!
      this.loginUser = loginUser
    }
  }
}
