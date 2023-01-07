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
