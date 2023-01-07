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

import com.zy.mylib.sys.config.entity.CodeItem
import com.zy.mylib.sys.config.dto.AddCodeItemRequest
import com.zy.mylib.sys.config.dto.UpdateCodeItemRequest
import com.zy.mylib.sys.config.dto.AddCodeItemResponse
import com.zy.mylib.sys.config.dto.UpdateCodeItemResponse
import com.zy.mylib.sys.config.dto.QueryCodeItemResponse
import com.zy.mylib.sys.config.dto.GetCodeItemResponse
import org.mapstruct.Mapper

/**
 * 代码项 DTO Response  mapper
 * @author 代码生成器
 */
@Mapper(componentModel = "spring")
interface CodeItemConvert {
  fun fromAddCodeItemRequest(req: AddCodeItemRequest): CodeItem
  fun fromUpdateCodeItemRequest(req: UpdateCodeItemRequest): CodeItem
  fun toAddCodeItemResponse(entity: CodeItem): AddCodeItemResponse
  fun toUpdateCodeItemResponse(entity: CodeItem): UpdateCodeItemResponse
  fun toQueryCodeItemResponse(entity: CodeItem): QueryCodeItemResponse
  fun toGetCodeItemResponse(entity: CodeItem): GetCodeItemResponse
}
