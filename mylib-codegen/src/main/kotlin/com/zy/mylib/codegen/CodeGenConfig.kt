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
package com.zy.mylib.codegen

import com.zy.mylib.utils.StringUtils.humpToLine

val fileExtMap = mapOf<String, String>("kotlin" to "kt", "java" to "java")

class CodeGenConfig {
  var lang: String = "kotlin"
  var genRest = false
  var srcPath: String? = ""
  lateinit var pkg: String

  val fileExtName: String
    get() = fileExtMap[lang]?:"java"
}

class EntityConfig {
  var superClass: String = "com.zy.mylib.data.jpa.UUIDBaseEntity"

  /**
   * jpa | mongo | mybatis
   */
  var type: String = "jpa"
  var pkg: String? = null
  lateinit var fields: List<FieldConfig>
  lateinit var name: String
  lateinit var caption: String
  var _tableName: String? = null

  val superClassName: String
    get() = superClass.substringAfterLast('.')
  var tableName: String
    get() = if(this._tableName.isNullOrBlank()) humpToLine(name) else this._tableName!!
    set(value) {
      this._tableName = value
    }
}

class FieldConfig {
  lateinit var name: String
  lateinit var caption: String

  /**
   * string | int | long | Datetime
   */
  var type = "String"
  var len = 0
  var _fieldName: String? = null
  var fieldName: String
    get() = if(_fieldName.isNullOrBlank()) humpToLine(name) else _fieldName!!
    set(value) {
      _fieldName = value
    }
}
