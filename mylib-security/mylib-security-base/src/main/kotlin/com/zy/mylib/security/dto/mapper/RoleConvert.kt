package com.zy.mylib.security.dto.mapper

import com.zy.mylib.security.entity.Role
import com.zy.mylib.security.dto.*
import org.mapstruct.Mapper

/**
 * 角色 DTO Response  mapper
 * @author 代码生成器
 */
@Mapper
interface RoleConvert {
  fun toRequest(entity: Role): RoleRequest
  fun toResponse(entity: Role): RoleResponse
  fun fromRequest(request: RoleRequest): Role
  fun fromResponse(response: RoleResponse): Role
}
