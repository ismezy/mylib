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

import com.zy.mylib.base.exception.BusException
import com.zy.mylib.webmvc.model.RestMessage
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/")
class JsonErrorController : ErrorController {
  @RequestMapping("/error")
  fun error(request: HttpServletRequest, response: HttpServletResponse): RestMessage {
    response.status = 400
    val e = request.getAttribute("javax.servlet.error.exception")
    if (e == null) {
      response.status = (request.getAttribute("javax.servlet.error.status_code") as Int)
      return RestMessage("" + response.status, request.getAttribute("javax.servlet.error.message") as String)
    } else if (e is BusException) {
      val ex = e
      response.status = ex.httpStatus
      return RestMessage(ex.code, ex.message)
    } else if((e as Throwable).cause is BusException) {
      val ex = e.cause as BusException
      response.status = ex.httpStatus
      return RestMessage(ex.code, ex.message)
    }
    return RestMessage("500", e.toString())
  }

}
