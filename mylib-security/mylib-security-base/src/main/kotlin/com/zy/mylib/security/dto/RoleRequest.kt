package com.zy.mylib.security.dto

import com.zy.mylib.mongo.model.BaseMongoModel

/**
 * 角色 DTO
 * @author 代码生成器
 */
class RoleRequest: BaseMongoModel() {
  var code: String? = null
  var caption: String? = null
}
