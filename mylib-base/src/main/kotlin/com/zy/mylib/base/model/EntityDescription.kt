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
package com.zy.mylib.base.model

import org.springframework.beans.factory.annotation.Autowired
import com.zy.mylib.base.i18n.LocalMessage
import com.zy.mylib.base.exception.BusException
import com.zy.mylib.base.model.BaseModel.BaseView
import com.zy.mylib.base.model.EntityDescription
import java.lang.RuntimeException
import lombok.NoArgsConstructor
import lombok.AllArgsConstructor
import com.zy.mylib.base.model.LogicalOperators
import com.zy.mylib.base.model.SortRequest
import com.zy.mylib.base.model.SortRequest.SortDirection
import com.zy.mylib.base.model.PageResponse
import com.zy.mylib.base.model.BaseModel
import com.zy.mylib.base.exception.BusException.BusExceptionBuilder
import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * @author ASUS
 */
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention(RetentionPolicy.RUNTIME)
@Documented
annotation class EntityDescription(
    /**
     * 实体描述
     *
     * @return
     */
    val value: String = ""
)