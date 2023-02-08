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
package com.zy.mylib.sys.config.rest

import com.zy.mylib.sys.config.dto.ConvertItem
import com.zy.mylib.sys.config.manager.ConvertService
import com.zy.mylib.webmvc.base.BaseRest
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.inject.Inject

/**
 * 数据字典转换接口
 */
@RestController
@RequestMapping("/convert")
@Api("数据字典转换接口")
class ConvertRest : BaseRest() {
  @Inject
  lateinit var convertService: ConvertService
  @GetMapping("{group}")
  @ApiOperation("获取转换列表")
  fun listByGroup(@PathVariable("group") group: String): List<ConvertItem> {
    return convertService.listByGroup(group)
  }
}