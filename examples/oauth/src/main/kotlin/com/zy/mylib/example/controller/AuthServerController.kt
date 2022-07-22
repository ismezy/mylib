package com.zy.mylib.example.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

/**
 * oauth2 服务接口
 */
@Controller
@RequestMapping("/oauth")
class AuthServerController {
  @GetMapping("/oauth")
  fun authorize(@RequestParam(name = "clientId") clientId: String, @RequestParam(name = "secret") secret: String) {

  }
}