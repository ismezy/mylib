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
package com.zy.mylib.sys.config.dto.mapper

import com.zy.mylib.sys.config.entity.GlobalConfig
import com.zy.mylib.sys.config.dto.AddGlobalConfigRequest
import com.zy.mylib.sys.config.dto.UpdateGlobalConfigRequest
import com.zy.mylib.sys.config.dto.AddGlobalConfigResponse
import com.zy.mylib.sys.config.dto.UpdateGlobalConfigResponse
import com.zy.mylib.sys.config.dto.QueryGlobalConfigResponse
import com.zy.mylib.sys.config.dto.GetGlobalConfigResponse
import org.mapstruct.Mapper

/**
 * 全局配置 DTO Response  mapper
 * @author 代码生成器
 */
@Mapper(componentModel = "spring")
interface GlobalConfigConvert {
  fun fromAddGlobalConfigRequest(req: AddGlobalConfigRequest): GlobalConfig
  fun fromUpdateGlobalConfigRequest(req: UpdateGlobalConfigRequest): GlobalConfig
  fun toAddGlobalConfigResponse(entity: GlobalConfig): AddGlobalConfigResponse
  fun toUpdateGlobalConfigResponse(entity: GlobalConfig): UpdateGlobalConfigResponse
  fun toQueryGlobalConfigResponse(entity: GlobalConfig): QueryGlobalConfigResponse
  fun toGetGlobalConfigResponse(entity: GlobalConfig): GetGlobalConfigResponse
}
