package com.zy.mylib.sys.config.dto

import com.zy.mylib.mongo.model.BaseMongoModel
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * 代码集 DTO
 * @author 代码生成器
 */
@ApiModel("代码集")
class UpdateCodeMapRequest: BaseMongoModel() {
  /**
   * 代码集编号
   */
  @ApiModelProperty("代码集编号")
  var code: String? = null
  /**
   * 代码集名
   */
  @ApiModelProperty("代码集名")
  var caption: String? = null
}
