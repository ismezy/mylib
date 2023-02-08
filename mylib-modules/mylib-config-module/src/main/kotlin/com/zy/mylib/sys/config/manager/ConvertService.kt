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
package com.zy.mylib.sys.config.manager

import com.zy.mylib.sys.config.dto.ConvertItem
import org.springframework.stereotype.Component
import javax.inject.Inject

/**
 * 字典服务组件
 */
@Component
class ConvertService {
  @Inject
  lateinit var codeItemManager: CodeItemManager

  fun listByGroup(group: String): List<ConvertItem> {
    return codeItemManager.findByCodemap(group).map {
      ConvertItem().apply {
        this.code = it.code
        this.value = it.value
        this.text = it.caption
      }
    }
  }
}