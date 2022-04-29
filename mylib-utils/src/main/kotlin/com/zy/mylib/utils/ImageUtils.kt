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
package com.zy.mylib.utils

import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.IOException
import java.io.OutputStream
import javax.imageio.ImageIO

/**
 * 图形工具类
 *
 * @author ASUS
 */
object ImageUtils {
  /**
   * 往output流绘制一个透明背景的文本图片
   *
   * @param text
   * @param width
   * @param height
   * @param font
   * @param output
   */
  @Throws(IOException::class)
  fun drawTextPNG(text: String?, width: Int, height: Int, font: Font?, output: OutputStream?) {
    val bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR)
    val g2d = bufferedImage.graphics as Graphics2D
    g2d.color = Color.black
    g2d.setRenderingHint(
      RenderingHints.KEY_TEXT_ANTIALIASING,
      RenderingHints.VALUE_TEXT_ANTIALIAS_ON
    )
    g2d.font = font
    val fontMetrics = g2d.fontMetrics
    val rect = fontMetrics.getStringBounds(text, g2d)
    g2d.drawString(
      text,
      (400 - rect.width.toInt()) / 2,
      (200 - rect.height.toInt()) / 2
    )

    //Free graphic resources
    g2d.dispose()

    //Write the image as a jpg
    ImageIO.write(bufferedImage, "png", output)
  }
}
