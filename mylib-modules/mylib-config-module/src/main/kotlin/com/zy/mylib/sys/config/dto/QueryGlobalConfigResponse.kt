package com.zy.mylib.sys.config.dto

import com.zy.mylib.mongo.model.BaseMongoModel
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * 全局配置 DTO
 * @author 代码生成器
 */
@ApiModel("全局配置")
class QueryGlobalConfigResponse: BaseMongoModel() {
  /**
   * 配置项编号
   */
  @ApiModelProperty("配置项编号")
  var code: String? = null
  /**
   * 配置项名称
   */
  @ApiModelProperty("配置项名称")
  var caption: String? = null
  /**
   * 配置项值
   */
  @ApiModelProperty("配置项值")
  var value: String? = null
}
