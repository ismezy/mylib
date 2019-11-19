package com.zy.mylib.utils;

import com.google.common.base.Strings;

import java.util.Random;

/**
 * 随机数工具
 */
public class RandomUtils {

  /**
   * 生成随机数字符串，长度不足时补0
   *
   * @param len 随机数长度
   * @return
   */
  public static String randNumberString(int len) {
    return randNumberString(len, '0');
  }

  /**
   * 生成随机数字符串
   *
   * @param len     随机数长度
   * @param padChar 被位数字符
   * @return
   */
  public static String randNumberString(int len, char padChar) {
    Random random = new Random();
    random.setSeed(System.currentTimeMillis());
    return Strings.padStart(Integer.toString(random.nextInt((int) Math.pow(10, len))), len, padChar);
  }

  /**
   * 6位随机数字，不足时前面补0
   *
   * @return
   */
  public static String randomFor6() {
    return randNumberString(6, '0');
  }
}
