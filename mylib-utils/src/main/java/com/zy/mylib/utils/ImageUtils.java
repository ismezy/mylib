package com.zy.mylib.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 图形工具类
 *
 * @author ASUS
 */
public class ImageUtils {
  /**
   * 往output流绘制一个透明背景的文本图片
   *
   * @param text
   * @param width
   * @param height
   * @param font
   * @param output
   */
  public static void drawTextPNG(String text, int width, int height, Font font, OutputStream output) throws IOException {
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
    Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
    g2d.setColor(Color.black);
    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
      RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    g2d.setFont(font);

    FontMetrics fontMetrics = g2d.getFontMetrics();
    Rectangle2D rect = fontMetrics.getStringBounds(text, g2d);

    g2d.drawString(text,
      (400 - (int) rect.getWidth()) / 2,
      (200 - (int) rect.getHeight()) / 2);

    //Free graphic resources
    g2d.dispose();

    //Write the image as a jpg
    ImageIO.write(bufferedImage, "png", output);
  }
}
