package com.zy.mylib.security.dto

import com.zy.mylib.mongo.model.BaseMongoModel
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * 角色 DTO
 * @author 代码生成器
 */
@ApiModel("角色")
class UpdateRoleRequest: BaseMongoModel() {
  /**
   * 角色code
   */
  @ApiModelProperty("角色code")
  var code: String? = null
  /**
   * 角色名称
   */
  @ApiModelProperty("角色名称")
  var caption: String? = null
  /**
   * 父角色代码
   */
  @ApiModelProperty("父角色代码")
  var parents: List<String>? = null
  /**
   * 授权
   */
  @ApiModelProperty("授权")
  var perms: List<String>? = null
}
