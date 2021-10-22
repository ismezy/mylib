/**
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
package com.zy.mylib.base.model

import java.lang.RuntimeException
import org.apache.commons.lang3.StringUtils
import java.io.Serializable
import javax.persistence.MappedSuperclass

/**
 * 基础模型
 *
 * @author ASUS
 */
@MappedSuperclass
interface BaseModel : Serializable {
  /**
   * JSON基础视图
   */
  interface BaseView

  /**
   * JSON列表视图
   */
  interface ListView : BaseView

  /**
   * JSON详细视图
   */
  interface DetailView : ListView

  /**
   * 添加验证
   */
  interface AddCheck

  /**
   * 修改验证
   */
  interface UpdateCheck

  /**
   * 实体描述
   *
   * @return
   */
  fun description(): String? {
    val entity = this.javaClass.kotlin.annotations.find { it is EntityDescription } as? EntityDescription
    if (entity != null && StringUtils.isNotBlank(entity.value)) {
      return entity.value
    }
    throw RuntimeException(this.javaClass.toGenericString() + "未添加@EntityDescription注解")
  }
}