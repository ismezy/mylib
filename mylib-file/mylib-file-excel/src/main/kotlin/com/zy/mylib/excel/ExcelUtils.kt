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
package com.zy.mylib.excel

import org.jxls.transform.poi.PoiTransformer
import org.jxls.util.JxlsHelper
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * excel工具类
 *
 * @author ASUS
 */
object ExcelUtils {
  /**
   * 生成excel文件
   * @param data 模板参数
   * @param template 模板
   * @param target 保存路径
   * @throws IOException
   */
  @JvmStatic
  @Throws(IOException::class)
  fun genExcel(data: Map<String?, Any?>, template: InputStream?, target: File?) {
    val jxlsHelper = JxlsHelper.getInstance()
    val context = PoiTransformer.createInitialContext()
    data.forEach { (key: String?, value: Any?) -> context.putVar(key, value) }
    val fileOutputStream = FileOutputStream(target)
    val trans = jxlsHelper.createTransformer(template, fileOutputStream)
    jxlsHelper.processTemplate(context, trans)
    fileOutputStream.close()
  }
}
