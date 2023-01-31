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
package com.zy.mylib.security.rest

import com.zy.mylib.security.manager.RoleManager
import com.zy.mylib.security.entity.Role
import org.springframework.web.bind.annotation.*
import com.fasterxml.jackson.annotation.JsonView
import com.zy.mylib.base.model.BaseModel
import com.zy.mylib.webmvc.base.BaseRest
import javax.inject.Inject
import com.zy.mylib.base.model.*
import com.zy.mylib.base.exception.BusException
import org.springframework.validation.annotation.Validated
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import com.zy.mylib.security.dto.AddRoleRequest
import com.zy.mylib.security.dto.AddRoleResponse
import com.zy.mylib.security.dto.UpdateRoleRequest
import com.zy.mylib.security.dto.UpdateRoleResponse
import com.zy.mylib.security.dto.GetRoleResponse
import com.zy.mylib.security.dto.QueryRoleResponse
import com.zy.mylib.security.dto.mapper.RoleConvert
import com.zy.mylib.webmvc.model.QueryWrapper

/**
 * 角色 rest接口
 * @author 代码生成器
 */
//@RestController
//@RequestMapping("/填写rest地址")
@Api(description = "角色接口")
class RoleRest: BaseRest() {
  @Inject
  private lateinit var manager: RoleManager
  @Inject
  private lateinit var convert: RoleConvert

  /**
   * 获取角色详情
   */
  @GetMapping("{id}")
  @ApiOperation("获取角色详情")
  fun findOne(@PathVariable("id") id: String): GetRoleResponse {
    var ret = manager.findById(id)
    if(ret != null) {
      return convert.toGetRoleResponse(ret)
    }
    throw BusException.builder().message("数据不存在").httpStatus(404).build()
  }

  /**
   * 创建角色
   */
  @ApiOperation("创建角色")
  @PostMapping
  fun addEntity(@Validated @RequestBody req: AddRoleRequest): AddRoleResponse {
    val entity = convert.fromAddRoleRequest(req)
    return convert.toAddRoleResponse(manager!!.add(entity))
  }
  /**
   * 更新角色
   */
  @ApiOperation("更新角色")
  @PutMapping
  fun updateEntity(@Validated @RequestBody req: UpdateRoleRequest): UpdateRoleResponse {
    val entity = convert.fromUpdateRoleRequest(req)
    return convert.toUpdateRoleResponse(manager.update(entity))
  }

  /**
   * 删除角色
   */
  @ApiOperation("删除角色")
  @DeleteMapping("{id}")
  fun remove(@PathVariable("id") id: String) {
    return manager.delete(id)
  }

  /**
   * 分页查询角色
   */
  @GetMapping("/pager")
  fun findPage(queryWrapper: QueryWrapper): PageResponse<QueryRoleResponse> {
    return manager.pageQuery(queryWrapper.page, queryWrapper.sort, queryWrapper.cond).let {
      PageResponse.fromRequest(queryWrapper.page, it.totalElement,
      it.list?.map { it1 -> convert.toQueryRoleResponse(it1) })
    }
  }
}
