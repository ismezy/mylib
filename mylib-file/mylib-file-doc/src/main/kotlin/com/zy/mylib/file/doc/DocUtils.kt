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

import fr.opensagres.xdocreport.document.images.IImageProvider
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry
import fr.opensagres.xdocreport.template.TemplateEngineKind
import org.apache.commons.io.IOUtils
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


object DocUtils {
  fun genDoc(data: Map<String, Any?>, template: InputStream, target: File) {

//    report.fieldsMetadata.load("users", UserInfo::class.java, true)
//    meta.load("users", UserInfo::class.java, true)
//    context.put("users", data["users"])
    FileOutputStream(target).use {
      val bytes = genDoc(data, template)
      ByteArrayInputStream(bytes).use { bis ->
        IOUtils.copy(bis, it)
      }
    }
  }

  fun genDoc(data: Map<String, Any?>, template: InputStream): ByteArray {
    //1.通过freemarker模板引擎加载文档，并缓存到registry中
    val report = XDocReportRegistry
        .getRegistry()
        .loadReport(template, TemplateEngineKind.Velocity)
    //2.设置填充字段、填充类以及是否为list。
    val context = report.createContext(data)
    val meta = report.createFieldsMetadata()
    data.forEach {
      if(it.value is List<*> || it.value is Array<*>) {
        meta.addFieldAsList(it.key)
      }
      if(it.value is IImageProvider) {
        meta.addFieldAsImage(it.key)
      }
    }

    ByteArrayOutputStream().use {
      report.process(context, it)
      return it.toByteArray()
    }
  }
}