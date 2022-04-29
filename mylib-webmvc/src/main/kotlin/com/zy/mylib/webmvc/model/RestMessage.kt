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
package com.zy.mylib.webmvc.model

import com.fasterxml.jackson.annotation.JsonView
import com.zy.mylib.base.model.BaseModel.BaseView

/**
 * @author 扬
 * @date 2017/1/10
 */
class RestMessage(
  /**
   * 代码
   */
  @JsonView(BaseView::class)
  var code: String?,
  /**
   * 消息
   */
  @JsonView(BaseView::class)
  var message: String?
) {
  companion object {
    /**
     * 通用成功消息
     */
    var SUCCESS: RestMessage? = null
    var UNKNOW_ERROR: RestMessage? = null

    init {
      SUCCESS = RestMessage("0000", "成功")
      UNKNOW_ERROR = RestMessage("9999", "未知错误")
    }
  }
}
