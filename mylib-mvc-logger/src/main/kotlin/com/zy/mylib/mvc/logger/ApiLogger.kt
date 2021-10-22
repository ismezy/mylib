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
package com.zy.mylib.mvc.logger

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * api日志记录注解
 *
 * @author 扬
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
annotation class ApiLogger(
    val type: String = "",
    val console: Boolean = true,
    /**
     * 是否记录数据库,默认true
     *
     * @return
     */
    val db: Boolean = true,
    /**
     * 请求文本模板，例：
     *
     * @return
     * @ApiLogger(request = "${loginUser?.userName}新增了字典[${codemap.code}]")
     * public String addCodemap(@RequestBody Codemap codemap);
     */
    val request: String = "",
    /**
     * 成功时的文本模板默认值为"|,成功[${returnValue}]。"，例：
     *
     * @return
     * @ApiLogger(request = "${loginUser?.userName}新增了字典[${codemap.code}]", success=",成功，id=${returnValue.id}")
     * public String addCodemap(@RequestBody Codemap codemap);
     */
    val success: String = ",成功。",
    /**
     * api失败时的文本模板默认值为",失败[${exception.message}]！"，例：
     *
     * @return
     * @ApiLogger(request = "${loginUser?.userName}新增了字典[${codemap.code}]", error=",失败，原因为${exception.message}")
     * public String addCodemap(@RequestBody Codemap codemap);
     */
    val error: String = ",失败[\${exception.message}]！",
    /**
     * 目标id
     *
     * @return
     */
    val id: String = "",
)