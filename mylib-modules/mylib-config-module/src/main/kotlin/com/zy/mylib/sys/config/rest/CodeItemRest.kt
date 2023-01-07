package com.zy.mylib.sys.config.rest

import com.zy.mylib.sys.config.manager.CodeItemManager
import org.springframework.web.bind.annotation.*
import com.zy.mylib.webmvc.base.BaseRest
import javax.inject.Inject
import com.zy.mylib.base.model.*
import com.zy.mylib.base.exception.BusException
import org.springframework.validation.annotation.Validated
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import com.zy.mylib.sys.config.dto.AddCodeItemRequest
import com.zy.mylib.sys.config.dto.AddCodeItemResponse
import com.zy.mylib.sys.config.dto.UpdateCodeItemRequest
import com.zy.mylib.sys.config.dto.UpdateCodeItemResponse
import com.zy.mylib.sys.config.dto.GetCodeItemResponse
import com.zy.mylib.sys.config.dto.QueryCodeItemResponse
import com.zy.mylib.sys.config.dto.mapper.CodeItemConvert

/**
 * 代码项 rest接口
 * @author 代码生成器
 */
@RestController
@RequestMapping("/sys/codeitem-manage")
@Api(description = "代码项接口")
class CodeItemRest: BaseRest() {
  @Inject
  private lateinit var manager: CodeItemManager
  @Inject
  private lateinit var convert: CodeItemConvert

  /**
   * 获取代码项详情
   */
  @GetMapping("{id}")
  @ApiOperation("获取代码项详情")
  fun findOne(@PathVariable("id") id: String): GetCodeItemResponse {
    var ret = manager.findById(id)
    if(ret != null) {
      return convert.toGetCodeItemResponse(ret)
    }
    throw BusException.builder().message("数据不存在").httpStatus(404).build()
  }

  /**
   * 创建代码项
   */
  @ApiOperation("创建代码项")
  @PostMapping
  fun addEntity(@Validated @RequestBody req: AddCodeItemRequest): AddCodeItemResponse {
    val entity = convert.fromAddCodeItemRequest(req)
    return convert.toAddCodeItemResponse(manager.add(entity))
  }
  /**
   * 更新代码项
   */
  @ApiOperation("更新代码项")
  @PutMapping
  fun updateEntity(@Validated @RequestBody req: UpdateCodeItemRequest): UpdateCodeItemResponse {
    val entity = convert.fromUpdateCodeItemRequest(req)
    return convert.toUpdateCodeItemResponse(manager.update(entity))
  }

  /**
   * 删除代码项
   */
  @ApiOperation("删除代码项")
  @DeleteMapping("{id}")
  fun remove(@PathVariable("id") id: String) {
    return manager.delete(id)
  }

  /**
   * 分页查询代码项
   */
  @ApiOperation("分页查询代码项")
  @GetMapping("/pager")
  fun findPage(pageRequest: PageRequest, conditions: List<Condition>): PageResponse<QueryCodeItemResponse> {
    return manager.pageQuery(pageRequest, conditions).let {
      PageResponse.fromRequest<QueryCodeItemResponse>(pageRequest, it.totalElement,
      it.list?.map { it1 -> convert.toQueryCodeItemResponse(it1) })
    }
  }
}