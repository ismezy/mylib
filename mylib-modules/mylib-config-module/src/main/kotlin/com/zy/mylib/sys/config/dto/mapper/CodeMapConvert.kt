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

import com.zy.mylib.sys.config.entity.CodeMap
import com.zy.mylib.sys.config.dto.AddCodeMapRequest
import com.zy.mylib.sys.config.dto.UpdateCodeMapRequest
import com.zy.mylib.sys.config.dto.AddCodeMapResponse
import com.zy.mylib.sys.config.dto.UpdateCodeMapResponse
import com.zy.mylib.sys.config.dto.QueryCodeMapResponse
import com.zy.mylib.sys.config.dto.GetCodeMapResponse
import org.mapstruct.Mapper

/**
 * 代码集 DTO Response  mapper
 * @author 代码生成器
 */
@Mapper(componentModel = "spring")
interface CodeMapConvert {
  fun fromAddCodeMapRequest(req: AddCodeMapRequest): CodeMap
  fun fromUpdateCodeMapRequest(req: UpdateCodeMapRequest): CodeMap
  fun toAddCodeMapResponse(entity: CodeMap): AddCodeMapResponse
  fun toUpdateCodeMapResponse(entity: CodeMap): UpdateCodeMapResponse
  fun toQueryCodeMapResponse(entity: CodeMap): QueryCodeMapResponse
  fun toGetCodeMapResponse(entity: CodeMap): GetCodeMapResponse
}
