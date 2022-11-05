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
package com.zy.mylib.example.controller

import me.zhyd.oauth.config.AuthConfig
import me.zhyd.oauth.model.AuthCallback
import me.zhyd.oauth.request.AuthGiteeRequest
import me.zhyd.oauth.request.AuthRequest
import me.zhyd.oauth.utils.AuthStateUtils
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import java.io.IOException
import javax.servlet.http.HttpServletResponse

/**
 * @author zhouyang
 */
@Controller
@RequestMapping("/oauth")
class AuthClientController {
  @RequestMapping("/render")
  @Throws(IOException::class)
  fun renderAuth(response: HttpServletResponse) {
    response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()))
  }

  @RequestMapping("/callback")
  fun login(callback: AuthCallback?): ModelAndView {
    val response = authRequest.login(callback)
    return ModelAndView("index", "user", response)
  }

  private val authRequest: AuthRequest
    private get() = AuthGiteeRequest(
      AuthConfig.builder()
        .clientId("db8009fb00e01d286bad65ae122b2ced56f0a41e9388efe905a2188d77074079")
        .clientSecret("a18d7532f484435e9c115231f86e54585db7bcf9d7206fe302c34cf919cd8139")
        .redirectUri("http://localhost:8080/oauth/callback")
        .build()
    )
}
