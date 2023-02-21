package com.zy.mylib.gridfs.rest

import com.zy.mylib.gridfs.manager.DirectoryInfoManager
import org.springframework.web.bind.annotation.*
import com.zy.mylib.webmvc.base.BaseRest
import javax.inject.Inject
import com.zy.mylib.base.model.*
import com.zy.mylib.base.exception.BusException
import com.zy.mylib.webmvc.model.QueryWrapper
import org.springframework.validation.annotation.Validated
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import com.zy.mylib.gridfs.dto.AddDirectoryInfoRequest
import com.zy.mylib.gridfs.dto.AddDirectoryInfoResponse
import com.zy.mylib.gridfs.dto.UpdateDirectoryInfoRequest
import com.zy.mylib.gridfs.dto.UpdateDirectoryInfoResponse
import com.zy.mylib.gridfs.dto.GetDirectoryInfoResponse
import com.zy.mylib.gridfs.dto.QueryDirectoryInfoResponse
import com.zy.mylib.gridfs.dto.mapper.DirectoryInfoConvert

/**
 * 目录信息 rest接口
 * @author 代码生成器
 */
//@RestController
//@RequestMapping("/填写rest地址")
@Api(description = "目录信息接口")
class DirectoryInfoRest: BaseRest() {
  @Inject
  private lateinit var manager: DirectoryInfoManager
  @Inject
  private lateinit var convert: DirectoryInfoConvert

  /**
   * 获取目录信息详情
   */
  @GetMapping("{id}")
  @ApiOperation("获取目录信息详情")
  fun findOne(@PathVariable("id") id: String): GetDirectoryInfoResponse {
    var ret = manager.findById(id)
    if(ret != null) {
      return convert.toGetDirectoryInfoResponse(ret)
    }
    throw BusException.builder().message("数据不存在").httpStatus(404).build()
  }

  /**
   * 创建目录信息
   */
  @ApiOperation("创建目录信息")
  @PostMapping
  fun addEntity(@Validated @RequestBody req: AddDirectoryInfoRequest): AddDirectoryInfoResponse {
    val entity = convert.fromAddDirectoryInfoRequest(req)
    return convert.toAddDirectoryInfoResponse(manager.add(entity))
  }
  /**
   * 更新目录信息
   */
  @ApiOperation("更新目录信息")
  @PutMapping
  fun updateEntity(@Validated @RequestBody req: UpdateDirectoryInfoRequest): UpdateDirectoryInfoResponse {
    val entity = convert.fromUpdateDirectoryInfoRequest(req)
    return convert.toUpdateDirectoryInfoResponse(manager.update(entity))
  }

  /**
   * 删除目录信息
   */
  @ApiOperation("删除目录信息")
  @DeleteMapping("{id}")
  fun remove(@PathVariable("id") id: String) {
    return manager.delete(id)
  }

  /**
   * 分页查询目录信息
   */
  @ApiOperation("分页查询目录信息")
  @GetMapping("/pager")
  fun findPage(queryWrapper: QueryWrapper): PageResponse<QueryDirectoryInfoResponse> {
    return manager.pageQuery(queryWrapper.page, queryWrapper.sort, queryWrapper.cond).let {
      PageResponse.fromRequest(queryWrapper.page, it.totalElement,
        it.list?.map { it1 -> convert.toQueryDirectoryInfoResponse(it1) })
    }
  }
}
