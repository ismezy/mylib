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

import com.zy.mylib.base.exception.BusException
import com.zy.mylib.webmvc.model.RestMessage
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping
class ErrorController : org.springframework.boot.web.servlet.error.ErrorController {
  @RequestMapping("/error")
  fun error(request: HttpServletRequest, response: HttpServletResponse): ModelAndView {
    val e = request.getAttribute("javax.servlet.error.exception")
    return if(e == null) {
      response.status = (request.getAttribute("javax.servlet.error.status_code") as Int)!!
      ModelAndView("forward:/error/json").apply {
        this.model["message"] = RestMessage("" + response.status, request.getAttribute("javax.servlet.error.message") as String)
      }
    } else if(e is BusException){
      if(e.httpStatus == 401) {
        ModelAndView("redirect:/login")
      } else {
        ModelAndView("${e.httpStatus}")
      }
    } else {
      ModelAndView("500")
    }
  }

}