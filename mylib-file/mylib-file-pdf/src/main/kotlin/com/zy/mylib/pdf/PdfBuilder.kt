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