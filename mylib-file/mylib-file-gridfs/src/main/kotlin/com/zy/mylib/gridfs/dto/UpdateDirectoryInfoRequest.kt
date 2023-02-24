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
 * 目录信息 DTO
 * @author 代码生成器
 */
@ApiModel("目录信息")
class UpdateDirectoryInfoRequest: BaseMongoModel() {
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
