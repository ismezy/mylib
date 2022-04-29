/*
 * Copyright © ${project.inceptionYear} ismezy (ismezy@qq.com)
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
package com.zy.mylib.webmvc.rest

import com.fasterxml.jackson.annotation.JsonView
import com.zy.mylib.base.model.BaseModel
import com.zy.mylib.base.model.BaseModel.AddCheck
import com.zy.mylib.base.model.BaseModel.DetailView
import com.zy.mylib.base.model.PageResponse
import com.zy.mylib.base.service.Manager
import com.zy.mylib.webmvc.base.BaseRest
import com.zy.mylib.webmvc.model.QueryWrapper
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import java.io.Serializable

/**
 * Created by 扬 on 2017/3/10.
 */
abstract class EntityRestController<T : BaseModel, PK : Serializable> : BaseRest() {
  /**
   * 获取manager
   *
   * @return
   */
  protected abstract fun getManager(): Manager<T, PK>

  @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
  @JsonView(
    DetailView::class
  )
  fun findOne(@PathVariable("id") id: PK): T? {
    return getManager().findById(id)
  }

  @RequestMapping(value = [""], method = [RequestMethod.POST])
  @JsonView(DetailView::class)
  fun addEntity(
    @Validated(
      AddCheck::class
    ) @RequestBody entity: T
  ): T? {
    return addEntityImpl(entity)
  }

  protected fun addEntityImpl(entity: T): T {
    return getManager().add(entity)
  }

  @RequestMapping(value = [""], method = [RequestMethod.PUT])
  @JsonView(DetailView::class)
  fun updateEntity(
    @Validated(
      BaseModel.UpdateCheck::class
    ) @RequestBody entity: T
  ): T {
    return updateEntityImpl(entity)
  }

  protected fun updateEntityImpl(entity: T): T {
    return getManager().update(entity)
  }

  @RequestMapping(value = ["/{id}"], method = [RequestMethod.DELETE])
  fun removeEntity(@PathVariable("id") id: PK) {
    getManager().delete(id)
  }

  @RequestMapping(value = [""], method = [RequestMethod.GET])
  @JsonView(BaseModel.ListView::class)
  fun queryList(queryWrapper: QueryWrapper): List<T>? {
    return getManager().findList(queryWrapper!!.conditions!!, queryWrapper.sorts!!)
  }

  @RequestMapping(value = ["/pager"], method = [RequestMethod.GET])
  @JsonView(BaseModel.ListView::class)
  fun queryPager(queryWrapper: QueryWrapper?): PageResponse<T>? {
    return getManager().pageQuery(queryWrapper!!.page!!, queryWrapper.conditions!!)
  }
}
