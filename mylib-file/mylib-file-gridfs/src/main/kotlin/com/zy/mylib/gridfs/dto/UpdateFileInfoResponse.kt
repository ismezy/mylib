/*
 * Copyright © 2020 ismezy (ismezy@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
class UpdateFileInfoResponse: BaseMongoModel() {
  /**
   * 文件全路径
   */
  @ApiModelProperty("文件全路径")
  var fullpath: String? = null
  /**
   * 文件名
   */
  @ApiModelProperty("文件名")
  var filename: String? = null
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
