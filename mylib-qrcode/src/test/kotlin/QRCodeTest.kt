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
