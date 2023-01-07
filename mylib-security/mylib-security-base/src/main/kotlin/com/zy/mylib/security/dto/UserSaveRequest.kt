package com.zy.mylib.security.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.util.*

/**
 * 保存用户请求参数
 */
@ApiModel("保存用户请求参数")
class UserSaveRequest {
  var id: String? = null;
  @ApiModelProperty("登录ID")
  var loginId: String? = null
  @ApiModelProperty("用户名称")
  var username: String? = null
  @ApiModelProperty("用户角色")
  var role = mutableListOf<String>()
}