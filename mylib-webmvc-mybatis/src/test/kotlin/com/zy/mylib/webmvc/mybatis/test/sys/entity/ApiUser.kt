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
package com.zy.mylib.webmvc.mybatis.test.sys.entity

import com.baomidou.mybatisplus.annotation.TableName
import com.zy.mylib.mybatis.entity.UuidBaseEntity

/**
 *
 *
 * Api用户
 *
 *
 * @author zhouyang
 * @since 2020-07-25
 */
@TableName
class ApiUser : UuidBaseEntity() {

    var code: String? = null

    var name: String? = null

    var secret: String? = null

    var system: String? = null

    companion object {
        private const val serialVersionUID = 3237411302995330359L
    }
}