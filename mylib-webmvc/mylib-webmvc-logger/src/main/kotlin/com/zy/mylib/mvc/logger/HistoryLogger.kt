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

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * 历史记录日志
 *
 * @author 扬
 * @date 2017/5/15
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
annotation class HistoryLogger(
    /**
     * 操作类型或功能名称
     *
     * @return
     */
    val operateType: String,
    /**
     * 历史记录实体，必须指向继承自HistoryEntity的实体，可指向多个
     *
     * @return
     */
    val historyEntities: Array<String>,
    /**
     * 操作用户
     *
     * @return
     */
    val user: String = "")