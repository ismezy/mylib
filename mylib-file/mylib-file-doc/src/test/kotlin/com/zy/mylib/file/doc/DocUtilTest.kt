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

import fr.opensagres.xdocreport.document.images.ClassPathImageProvider
import fr.opensagres.xdocreport.document.images.IImageProvider
import org.junit.jupiter.api.Test
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class DocUtilTest {
  @Test
  fun genDoc() {
    val map = mutableMapOf<String, Any?>()
    val image: IImageProvider = ClassPathImageProvider(DocUtilTest::class.java.classLoader, "test.jpg", false)
    val image1: IImageProvider = ClassPathImageProvider(DocUtilTest::class.java.classLoader, "test1.png", false)
    map.put("swjgmc", "成都")
    map.put("wszg", "2023年02月23")
    map.put("logo", image)
    map.put("users", listOf(
        UserInfo().apply { name = "zy"; sex = "男"; age = 45; photo = image },
        UserInfo().apply { name = "wbs"; sex = "男"; age = 35; photo = image1 },
        UserInfo().apply { name = "czx"; sex = "男"; age = 30; photo = image },
//        mapOf("name" to "zy", "sex" to "男", "age" to 45, "photo" to image),
//        mapOf("name" to "wbs", "sex" to "男", "age" to 35, "photo" to image),
//        mapOf("name" to "czx", "sex" to "男", "age" to 30, "photo" to image),

        ))

    val metaList = listOf(
        MetaInfo().apply { key =  "users"; classes = UserInfo::class.java; list = true}
    )

    ClassLoader.getSystemResourceAsStream("test.docx").use {
      val target = File.createTempFile("test_${Date().time}", ".docx")
      DocUtils.genDoc(map, it, target, metaList)
      println("---------------${target.path}")
    }
  }
}