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
