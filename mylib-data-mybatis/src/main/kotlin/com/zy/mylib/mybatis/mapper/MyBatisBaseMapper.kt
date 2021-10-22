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
package com.zy.mylib.mybatis.mapper

import com.zy.mylib.base.model.SortRequest
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.zy.mylib.mybatis.utils.QueryWrapperUtils
import com.zy.mylib.base.model.LogicalOperators
import com.zy.mylib.base.model.BaseModel
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import java.io.Serializable

/**
 * @author ASUS
 */
interface MyBatisBaseMapper<T : BaseModel?, PK : Serializable?> : BaseMapper<T>