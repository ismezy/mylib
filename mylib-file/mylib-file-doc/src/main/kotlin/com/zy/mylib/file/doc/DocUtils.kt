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

import com.deepoove.poi.XWPFTemplate
import com.deepoove.poi.config.Configure
import com.deepoove.poi.plugin.table.LoopColumnTableRenderPolicy
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy
import org.apache.commons.io.IOUtils
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


object DocUtils {
  fun genDoc(data: Map<String, Any?>, template: InputStream, target: File, typeMap: Map<String, String> = mapOf()) {
    FileOutputStream(target).use {
      val bytes = genDoc(data, template, typeMap)
      ByteArrayInputStream(bytes).use { bis ->
        IOUtils.copy(bis, it)
      }
    }
  }

  fun genDoc(data: Map<String, Any?>, template: InputStream, typeMap: Map<String, String> = mapOf()): ByteArray {
    var tableRowPlugin = LoopRowTableRenderPolicy()
    var tableColumnPlugin = LoopColumnTableRenderPolicy()
    var pluginMap = mapOf("tableRow" to tableRowPlugin, "tableColumn" to tableColumnPlugin)
    var config = Configure.builder()

    for (entry in data.entries) {
      if(typeMap.containsKey(entry.key) && pluginMap.containsKey(typeMap[entry.key])) {
        config = config.bind(entry.key, pluginMap[typeMap[entry.key]])
      } else if(entry.value is List<*>) {
        // 默认数组为table列表
        config = config.bind(entry.key, tableRowPlugin)
      }
    }

    var report = XWPFTemplate.compile(template, config.build())
    report = report.render(data)
    return ByteArrayOutputStream().use {
      report.write(it)
      return it.toByteArray()
    }
  }
}