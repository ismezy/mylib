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
package com.zy.mylib.gridfs.rest

import com.zy.mylib.gridfs.manager.FileInfoManager
import org.springframework.web.bind.annotation.*
import com.zy.mylib.webmvc.base.BaseRest
import javax.inject.Inject
import com.zy.mylib.base.model.*
import com.zy.mylib.base.exception.BusException
import com.zy.mylib.webmvc.model.QueryWrapper
import org.springframework.validation.annotation.Validated
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import com.zy.mylib.gridfs.dto.AddFileInfoRequest
import com.zy.mylib.gridfs.dto.AddFileInfoResponse
import com.zy.mylib.gridfs.dto.UpdateFileInfoRequest
import com.zy.mylib.gridfs.dto.UpdateFileInfoResponse
import com.zy.mylib.gridfs.dto.GetFileInfoResponse
import com.zy.mylib.gridfs.dto.QueryFileInfoResponse
import com.zy.mylib.gridfs.dto.mapper.FileInfoConvert

/**
 * 文件信息 rest接口
 * @author 代码生成器
 */
//@RestController
//@RequestMapping("/填写rest地址")
@Api(description = "文件信息接口")
class FileInfoRest: BaseRest() {
  @Inject
  private lateinit var manager: FileInfoManager
  @Inject
  private lateinit var convert: FileInfoConvert

  /**
   * 获取文件信息详情
   */
  @GetMapping("{id}")
  @ApiOperation("获取文件信息详情")
  fun findOne(@PathVariable("id") id: String): GetFileInfoResponse {
    var ret = manager.findById(id)
    if(ret != null) {
      return convert.toGetFileInfoResponse(ret)
    }
    throw BusException.builder().message("数据不存在").httpStatus(404).build()
  }

  /**
   * 创建文件信息
   */
  @ApiOperation("创建文件信息")
  @PostMapping
  fun addEntity(@Validated @RequestBody req: AddFileInfoRequest): AddFileInfoResponse {
    val entity = convert.fromAddFileInfoRequest(req)
    return convert.toAddFileInfoResponse(manager.add(entity))
  }
  /**
   * 更新文件信息
   */
  @ApiOperation("更新文件信息")
  @PutMapping
  fun updateEntity(@Validated @RequestBody req: UpdateFileInfoRequest): UpdateFileInfoResponse {
    val entity = convert.fromUpdateFileInfoRequest(req)
    return convert.toUpdateFileInfoResponse(manager.update(entity))
  }

  /**
   * 删除文件信息
   */
  @ApiOperation("删除文件信息")
  @DeleteMapping("{id}")
  fun remove(@PathVariable("id") id: String) {
    return manager.delete(id)
  }

  /**
   * 分页查询文件信息
   */
  @ApiOperation("分页查询文件信息")
  @GetMapping("/pager")
  fun findPage(queryWrapper: QueryWrapper): PageResponse<QueryFileInfoResponse> {
    return manager.pageQuery(queryWrapper.page, queryWrapper.sort, queryWrapper.cond).let {
      PageResponse.fromRequest(queryWrapper.page, it.totalElement,
        it.list?.map { it1 -> convert.toQueryFileInfoResponse(it1) })
    }
  }
}
