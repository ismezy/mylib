package com.zy.mylib.security.dto.mapper

import com.zy.mylib.security.entity.Role
import com.zy.mylib.security.dto.AddRoleRequest
import com.zy.mylib.security.dto.UpdateRoleRequest
import com.zy.mylib.security.dto.AddRoleResponse
import com.zy.mylib.security.dto.UpdateRoleResponse
import com.zy.mylib.security.dto.QueryRoleResponse
import com.zy.mylib.security.dto.GetRoleResponse
import org.mapstruct.Mapper

/**
 * 角色 DTO Response  mapper
 * @author 代码生成器
 */
@Mapper(componentModel = "spring")
interface RoleConvert {
  fun fromAddRoleRequest(req: AddRoleRequest): Role
  fun fromUpdateRoleRequest(req: UpdateRoleRequest): Role
  fun toAddRoleResponse(entity: Role): AddRoleResponse
  fun toUpdateRoleResponse(entity: Role): UpdateRoleResponse
  fun toQueryRoleResponse(entity: Role): QueryRoleResponse
  fun toGetRoleResponse(entity: Role): GetRoleResponse
}
