package com.zy.mylib.gridfs.dto

import com.zy.mylib.mongo.model.BaseMongoModel
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.util.*

/**
 * 文件信息 DTO
 * @author 代码生成器
 */
@ApiModel("文件信息")
class GetFileInfoResponse: BaseMongoModel() {
  /**
   * 文件全路径
   */
  @ApiModelProperty("文件全路径")
  var fullpath: String? = null
  /**
   * 文件Id
   */
  @ApiModelProperty("文件Id")
  var fileId: String? = null
  /**
   * 文件扩展名
   */
  @ApiModelProperty("文件扩展名")
  var extName: String? = null
  /**
   * 原文件名
   */
  @ApiModelProperty("原文件名")
  var originName: String? = null
  /**
   * 文件所在路径
   */
  @ApiModelProperty("文件所在路径")
  var path: String? = null
  /**
   * 文件创建时间
   */
  @ApiModelProperty("文件创建时间")
  var createTime: Date? = null
  /**
   * 文件修改时候
   */
  @ApiModelProperty("文件修改时候")
  var modifyTime: Date? = null
  /**
   * 文件大小
   */
  @ApiModelProperty("文件大小")
  var fileSize: Long? = null
}
