package com.zy.mylib.sys.config.rest

import com.zy.mylib.sys.config.manager.CodeMapManager
import org.springframework.web.bind.annotation.*
import com.zy.mylib.webmvc.base.BaseRest
import javax.inject.Inject
import com.zy.mylib.base.model.*
import com.zy.mylib.base.exception.BusException
import org.springframework.validation.annotation.Validated
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import com.zy.mylib.sys.config.dto.AddCodeMapRequest
import com.zy.mylib.sys.config.dto.AddCodeMapResponse
import com.zy.mylib.sys.config.dto.UpdateCodeMapRequest
import com.zy.mylib.sys.config.dto.UpdateCodeMapResponse
import com.zy.mylib.sys.config.dto.GetCodeMapResponse
import com.zy.mylib.sys.config.dto.QueryCodeMapResponse
import com.zy.mylib.sys.config.dto.mapper.CodeMapConvert

/**
 * 代码集 rest接口
 * @author 代码生成器
 */
@RestController
@RequestMapping("/sys/codemap-manage")
@Api(description = "代码集接口")
class CodeMapRest: BaseRest() {
  @Inject
  private lateinit var manager: CodeMapManager
  @Inject
  private lateinit var convert: CodeMapConvert

  /**
   * 获取代码集详情
   */
  @GetMapping("{id}")
  @ApiOperation("获取代码集详情")
  fun findOne(@PathVariable("id") id: String): GetCodeMapResponse {
    var ret = manager.findById(id)
    if(ret != null) {
      return convert.toGetCodeMapResponse(ret)
    }
    throw BusException.builder().message("数据不存在").httpStatus(404).build()
  }

  /**
   * 创建代码集
   */
  @ApiOperation("创建代码集")
  @PostMapping
  fun addEntity(@Validated @RequestBody req: AddCodeMapRequest): AddCodeMapResponse {
    val entity = convert.fromAddCodeMapRequest(req)
    return convert.toAddCodeMapResponse(manager.add(entity))
  }
  /**
   * 更新代码集
   */
  @ApiOperation("更新代码集")
  @PutMapping
  fun updateEntity(@Validated @RequestBody req: UpdateCodeMapRequest): UpdateCodeMapResponse {
    val entity = convert.fromUpdateCodeMapRequest(req)
    return convert.toUpdateCodeMapResponse(manager.update(entity))
  }

  /**
   * 删除代码集
   */
  @ApiOperation("删除代码集")
  @DeleteMapping("{id}")
  fun remove(@PathVariable("id") id: String) {
    return manager.delete(id)
  }

  /**
   * 分页查询代码集
   */
  @ApiOperation("分页查询代码集")
  @GetMapping("/pager")
  fun findPage(pageRequest: PageRequest, conditions: List<Condition>): PageResponse<QueryCodeMapResponse> {
    return manager.pageQuery(pageRequest, conditions).let {
      PageResponse.fromRequest<QueryCodeMapResponse>(pageRequest, it.totalElement,
      it.list?.map { it1 -> convert.toQueryCodeMapResponse(it1) })
    }
  }
}
