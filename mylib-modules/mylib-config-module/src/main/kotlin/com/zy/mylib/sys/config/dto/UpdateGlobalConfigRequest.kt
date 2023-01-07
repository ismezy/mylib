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
package com.zy.mylib.sys.config.dto

import com.zy.mylib.mongo.model.BaseMongoModel
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * 全局配置 DTO
 * @author 代码生成器
 */
@ApiModel("全局配置")
class UpdateGlobalConfigRequest: BaseMongoModel() {
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
