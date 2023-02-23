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
package com.zy.mylib.file.doc

import org.junit.jupiter.api.Test
import java.io.File
import java.util.*

class DocUtilTest {
  @Test
  fun genDoc() {
    val map = mutableMapOf<String, Any>()
    map.put("swjgmc", "成都")
    map.put("wszg", "2023年02月23")
    map.put("users", listOf(
        mapOf("name"  to "zy", "sex" to "男", "age" to 45),
        mapOf("name"  to "wbs", "sex" to "男", "age" to 35),
        mapOf("name"  to "czx", "sex" to "男", "age" to 30),
    ))
    ClassLoader.getSystemResourceAsStream("test.docx").use {
      DocUtils.genDoc(map, it, File("e:/temp/${Date().time}.docx"))
    }
  }
}