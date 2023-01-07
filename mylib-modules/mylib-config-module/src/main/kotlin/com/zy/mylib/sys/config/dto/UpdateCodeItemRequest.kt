package com.zy.mylib.sys.config.dto

import com.zy.mylib.mongo.model.BaseMongoModel
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * 代码项 DTO
 * @author 代码生成器
 */
@ApiModel("代码项")
class UpdateCodeItemRequest: BaseMongoModel() {
  /**
   * 代码项编号
   */
  @ApiModelProperty("代码项编号")
  var code: String? = null
  /**
   * 代码项名
   */
  @ApiModelProperty("代码项名")
  var caption: String? = null
  /**
   * 代码集代码
   */
  @ApiModelProperty("代码集代码")
  var codemap: String? = null
  /**
   * 排序数
   */
  @ApiModelProperty("排序数")
  var sortNum: Double? = null
  /**
   * 代码项值
   */
  @ApiModelProperty("代码项值")
  var value: String? = null
}
