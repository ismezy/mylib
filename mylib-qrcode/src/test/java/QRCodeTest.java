import com.google.zxing.WriterException;
import com.mylib.qrcode.QRCodeUtils;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QRCodeTest {
    /**
     * 测试生成二维码，不带logo
     */
    @Test
    public void testBuildQrCode() throws WriterException, IOException {
        BufferedImage bufferedImage = QRCodeUtils.buildQrCode("hello world", 800, 800, null);
        ImageIO.write(bufferedImage, "PNG", new File("/qrcode.png"));
    }

    @Test
    public void testBuildQrCodeWithLogo() throws WriterException, IOException {
        BufferedImage logo = ImageIO.read(ClassLoader.getSystemResourceAsStream("logo.jpg"));
        BufferedImage bufferedImage = QRCodeUtils.buildQrCode("hello world", 800, 800, logo);
        ImageIO.write(bufferedImage, "PNG", new File("/qrcode-with-logo.png"));
    }
}
