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
import com.google.zxing.WriterException
import com.zy.mylib.qrcode.QRCodeUtils.buildQrCode
import org.junit.jupiter.api.Test
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

class QRCodeTest {
  /**
   * 测试生成二维码，不带logo
   */
  @Test
  @Throws(WriterException::class, IOException::class)
  fun testBuildQrCode() {
    val bufferedImage = buildQrCode("hello world", 800, 800, "", null)
    ImageIO.write(bufferedImage, "PNG", File("/qrcode.png"))
  }

  @Test
  @Throws(WriterException::class, IOException::class)
  fun testBuildQrCodeWithLogo() {
    val logo = ImageIO.read(ClassLoader.getSystemResourceAsStream("logo.png"))
    val bufferedImage = buildQrCode(
      "hello world",
      800, 800, "测试标题", logo
    )
    ImageIO.write(bufferedImage, "PNG", File("/qrcode-with-logo.png"))
  }
}
