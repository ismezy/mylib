package com.mylib.qrcode;

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
        String format = "png";
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
        BufferedImage scaledOverlay = scaleOverlay(logo);

        Integer deltaHeight = qrcode.getHeight() - scaledOverlay.getHeight();
        Integer deltaWidth = qrcode.getWidth() - scaledOverlay.getWidth();

        BufferedImage combined = new BufferedImage(qrcode.getWidth(), logo.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) combined.getGraphics();
        g2.drawImage(qrcode, 0, 0, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g2.drawImage(scaledOverlay, Math.round(deltaWidth / 2), Math.round(deltaHeight / 2), null);
        return combined;
    }

    private static BufferedImage scaleOverlay(BufferedImage qrcode) {
        Integer scaledWidth = Math.round(qrcode.getWidth() * 0.2f);
        Integer scaledHeight = Math.round(qrcode.getHeight() * 0.2f);

        BufferedImage imageBuff = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics g = imageBuff.createGraphics();
        g.drawImage(qrcode.getScaledInstance(scaledWidth, scaledHeight, BufferedImage.SCALE_SMOOTH), 0, 0, new Color(0, 0, 0), null);
        g.dispose();
        return imageBuff;
    }
}
