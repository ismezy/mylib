package com.zy.mylib.security.dto

/**
 * 用户密码登录请求参数
 */
class PwdLoginRequest {
  lateinit var loginId: String
  lateinit var password: String
  var kaptcha: String? = null
}