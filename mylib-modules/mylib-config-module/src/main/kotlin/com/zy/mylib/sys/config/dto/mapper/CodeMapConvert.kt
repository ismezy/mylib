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
