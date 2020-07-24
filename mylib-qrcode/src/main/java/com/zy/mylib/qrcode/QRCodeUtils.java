package com.zy.mylib.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码工具类
 */
public class QRCodeUtils {
    /**
     * 生成二维码
     * @param content 二维码内容
     * @param width 宽度(像素)
     * @param height 高度(像素)
     * @param logo 图标，为空时不生成中心图标
     * @return
     * @throws WriterException
     */
    public static BufferedImage buildQrCode(String content, int width, int height, BufferedImage logo) throws WriterException {
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>(2);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.MARGIN, 0);
        BitMatrix bitMatrix = null;
        bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        BufferedImage qrcode = MatrixToImageWriter.toBufferedImage(bitMatrix);
        if (logo != null) {
            qrcode = getQRCodeWithOverlay(qrcode, logo);
        }
        return qrcode;
    }

    private static BufferedImage getQRCodeWithOverlay(BufferedImage qrcode, BufferedImage logo) {
        int centerX = qrcode.getWidth() / 2;
        int centerY = qrcode.getHeight() / 2;
        float radius = qrcode.getWidth() * 0.2f;

        BufferedImage combined = new BufferedImage(qrcode.getWidth(), qrcode.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) combined.getGraphics();
        g2.drawImage(qrcode, 0, 0, null);
        g2.setColor(Color.gray);
        g2.fillArc((int)(centerX - radius * 1.6/ 2) , (int)(centerY - radius * 1.6/ 2),
                (int)(radius * 1.6), (int)(radius * 1.6), 0, 360);
        g2.setColor(Color.white);
        g2.fillArc((int)(centerX - radius * 1.5 / 2) , (int)(centerY - radius * 1.5 / 2),
                (int)(radius * 1.5), (int)(radius * 1.5), 0, 360);
        g2.drawImage(logo, (int)(centerX - radius / 2) , (int)(centerY - radius / 2),
                (int)radius, (int)radius, Color.white, null);
        g2.dispose();
        return combined;
    }

}