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
package com.zy.mylib.pdf

import org.springframework.stereotype.Component
import org.xhtmlrenderer.pdf.ITextRenderer
import java.io.File
import java.io.InputStream


/**
 * pdf文件构建
 */
@Component
class PdfBuilder {
  /**
   * HTML转PDF
   */
  fun fromHtml(inputStream: InputStream): File {
    val renderer = ITextRenderer()
    return File("")
  }

  fun fromHtml(html: String): File {
    val renderer = ITextRenderer()
    renderer.setDocumentFromString(html)
//    File()
    return File("")
  }
}