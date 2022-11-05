package com.zy.mylib.security.dto.mapper

import com.zy.mylib.security.entity.RoleExtend
import com.zy.mylib.security.dto.*
import org.mapstruct.Mapper

/**
 * 角色 DTO Response  mapper
 * @author 代码生成器
 */
@Mapper
interface RoleExtendConvert {
  fun toRequest(entity: RoleExtend): RoleExtendRequest
  fun toResponse(entity: RoleExtend): RoleExtendResponse
  fun fromRequest(request: RoleExtendRequest): RoleExtend
  fun fromResponse(response: RoleExtendResponse): RoleExtend
}
