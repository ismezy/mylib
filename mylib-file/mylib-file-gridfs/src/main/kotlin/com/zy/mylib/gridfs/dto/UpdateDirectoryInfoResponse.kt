package com.zy.mylib.gridfs.dto

import com.zy.mylib.mongo.model.BaseMongoModel
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.util.*

/**
 * 目录信息 DTO
 * @author 代码生成器
 */
@ApiModel("目录信息")
class UpdateDirectoryInfoResponse: BaseMongoModel() {
  /**
   * 目录全路径
   */
  @ApiModelProperty("目录全路径")
  var fullpath: String? = null
  /**
   * 目录名
   */
  @ApiModelProperty("目录名")
  var filename: String? = null
  /**
   * 目录所在路径
   */
  @ApiModelProperty("目录所在路径")
  var path: String? = null
  /**
   * 目录创建时间
   */
  @ApiModelProperty("目录创建时间")
  var createTime: Date? = null
  /**
   * 文件修改时候
   */
  @ApiModelProperty("文件修改时候")
  var modifyTime: Date? = null
  /**
   * 目录下所有文件大小
   */
  @ApiModelProperty("目录下所有文件大小")
  var directorySize: Long? = null
}
