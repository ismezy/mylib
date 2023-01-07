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
  fun findPage(pageRequest: PageRequest, conditions: List<Condition>): PageResponse<QueryRoleResponse> {
    return manager.pageQuery(pageRequest, conditions).let {
      PageResponse.fromRequest<QueryRoleResponse>(pageRequest, it.totalElement,
      it.list?.map { it1 -> convert.toQueryRoleResponse(it1) })
    }
  }
}
