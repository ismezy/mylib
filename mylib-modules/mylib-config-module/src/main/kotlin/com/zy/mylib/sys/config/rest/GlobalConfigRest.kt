package com.zy.mylib.sys.config.rest

import com.zy.mylib.sys.config.manager.GlobalConfigManager
import org.springframework.web.bind.annotation.*
import com.zy.mylib.webmvc.base.BaseRest
import javax.inject.Inject
import com.zy.mylib.base.model.*
import com.zy.mylib.base.exception.BusException
import org.springframework.validation.annotation.Validated
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import com.zy.mylib.sys.config.dto.AddGlobalConfigRequest
import com.zy.mylib.sys.config.dto.AddGlobalConfigResponse
import com.zy.mylib.sys.config.dto.UpdateGlobalConfigRequest
import com.zy.mylib.sys.config.dto.UpdateGlobalConfigResponse
import com.zy.mylib.sys.config.dto.GetGlobalConfigResponse
import com.zy.mylib.sys.config.dto.QueryGlobalConfigResponse
import com.zy.mylib.sys.config.dto.mapper.GlobalConfigConvert

/**
 * 全局配置 rest接口
 * @author 代码生成器
 */
@RestController
@RequestMapping("/sys/global-config-manage")
@Api(description = "全局配置接口")
class GlobalConfigRest: BaseRest() {
  @Inject
  private lateinit var manager: GlobalConfigManager
  @Inject
  private lateinit var convert: GlobalConfigConvert

  /**
   * 获取全局配置详情
   */
  @GetMapping("{id}")
  @ApiOperation("获取全局配置详情")
  fun findOne(@PathVariable("id") id: String): GetGlobalConfigResponse {
    var ret = manager.findById(id)
    if(ret != null) {
      return convert.toGetGlobalConfigResponse(ret)
    }
    throw BusException.builder().message("数据不存在").httpStatus(404).build()
  }

  /**
   * 创建全局配置
   */
  @ApiOperation("创建全局配置")
  @PostMapping
  fun addEntity(@Validated @RequestBody req: AddGlobalConfigRequest): AddGlobalConfigResponse {
    val entity = convert.fromAddGlobalConfigRequest(req)
    return convert.toAddGlobalConfigResponse(manager.add(entity))
  }
  /**
   * 更新全局配置
   */
  @ApiOperation("更新全局配置")
  @PutMapping
  fun updateEntity(@Validated @RequestBody req: UpdateGlobalConfigRequest): UpdateGlobalConfigResponse {
    val entity = convert.fromUpdateGlobalConfigRequest(req)
    return convert.toUpdateGlobalConfigResponse(manager.update(entity))
  }

  /**
   * 删除全局配置
   */
  @ApiOperation("删除全局配置")
  @DeleteMapping("{id}")
  fun remove(@PathVariable("id") id: String) {
    return manager.delete(id)
  }

  /**
   * 分页查询全局配置
   */
  @ApiOperation("分页查询全局配置")
  @GetMapping("/pager")
  fun findPage(pageRequest: PageRequest, conditions: List<Condition>): PageResponse<QueryGlobalConfigResponse> {
    return manager.pageQuery(pageRequest, conditions).let {
      PageResponse.fromRequest<QueryGlobalConfigResponse>(pageRequest, it.totalElement,
      it.list?.map { it1 -> convert.toQueryGlobalConfigResponse(it1) })
    }
  }
}
