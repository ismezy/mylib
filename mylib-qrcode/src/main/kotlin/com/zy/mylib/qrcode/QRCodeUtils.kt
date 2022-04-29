/*
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
package com.zy.mylib.qrcode

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.awt.Color
import java.awt.Font
import java.awt.RenderingHints
import java.awt.image.BufferedImage

/**
 * 二维码工具类
 */
object QRCodeUtils {
  /**
   * 生成二维码
   *
   * @param content 二维码内容
   * @param width   宽度(像素)
   * @param height  高度(像素)
   * @param logo    图标，为空时不生成中心图标
   * @return
   * @throws WriterException
   */
  @JvmStatic
  @Throws(WriterException::class)
  fun buildQrCode(content: String?, width: Int, height: Int, title: String, logo: BufferedImage?): BufferedImage {
    val hints: MutableMap<EncodeHintType, Any?> = HashMap(2)
    hints[EncodeHintType.CHARACTER_SET] = "UTF-8"
    hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
    hints[EncodeHintType.MARGIN] = 0
    var bitMatrix: BitMatrix? = null
    bitMatrix = MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints)
    var qrcode = MatrixToImageWriter.toBufferedImage(bitMatrix)
    if (logo != null) {
      qrcode = getQRCodeWithOverlay(qrcode, logo, title)
    }
    return qrcode
  }

  private fun getQRCodeWithOverlay(qrcode: BufferedImage, logo: BufferedImage, title: String): BufferedImage {
    val centerX = qrcode.width / 2
    val centerY = qrcode.height / 2
    val radius = qrcode.width * 0.3f
    val round = (qrcode.width * 0.05f).toInt()
    val combined = BufferedImage(qrcode.width, qrcode.height, BufferedImage.TYPE_INT_ARGB)
    val g2 = combined.createGraphics()
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    g2.drawImage(qrcode, 0, 0, null)
    //    g2.setColor(Color.white);
//    g2.fillRoundRect((int) (centerX - radius * 1.5 / 2), (int) (centerY - radius * 1.5 / 2),
//        (int) (radius * 1.5), (int) (radius * 1.5), round, round);
    g2.drawImage(
      logo,
      (centerX - radius / 2).toInt(),
      (centerY - radius / 2).toInt(),
      radius.toInt(),
      radius.toInt(),
      null
    )
    g2.color = Color.black
    g2.font = Font("simsum", Font.PLAIN, (centerX * 0.05f).toInt())
    val fm = g2.fontMetrics
    val r = fm.getStringBounds(title, g2)
    val stringX = r.centerX.toInt()
    val stringY = r.centerY.toInt()
    g2.drawString(title, (centerX - stringX).toFloat(), centerY + radius / 2 - stringY)
    g2.dispose()
    return combined
  }
}
