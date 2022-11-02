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
package com.zy.mylib.mvc.logger

import com.zy.mylib.security.LoginUser

/**
 * 实体操作历史服务
 *
 * @author 扬
 * @date 2017/5/15
 */
interface OperateHistoryService<UT : LoginUser?> {
  /**
   * 新增历史记录
   *
   * @param json   实体JSON
   * @param type   操作类型
   * @param user   操作用户
   * @param target 目标实体
   */
  fun addHistory(json: String?, type: String?, user: UT?, target: Any?)
}