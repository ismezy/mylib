package com.zy.mylib.utils;

import com.google.common.base.Strings;

import java.util.Arrays;

/**
 * @author 扬
 * @date 2017/5/15
 */
public class StringUtils {
  /**
   * 首字母大写
   *
   * @param word
   * @return 首字母大写的字符串
   */
  public static String firstUpperCase(String word) {
    if (Strings.isNullOrEmpty(word)) {
      return word;
    }
    return word.substring(0, 1).toUpperCase() + (word.length() >= 2 ? word.substring(1) : "");
  }

  /**
   * 连接字符串
   *
   * @param array     字符串数组
   * @param delimiter 分隔符
   * @param start     数组起始位置
   * @param end       数组结束位置
   * @return
   */
  public static String join(String[] array, CharSequence delimiter, int start, int end) {
    return String.join(delimiter, Arrays.copyOfRange(array, start, end));
  }

  /**
   * 连接字符串
   *
   * @param array     字符串数组
   * @param delimiter 分隔符
   * @param start     数组起始位置
   * @return
   */
  public static String join(String[] array, CharSequence delimiter, int start) {
    return String.join(delimiter, Arrays.copyOfRange(array, start, array.length));
  }

  /**
   * 连接字符串
   *
   * @param delimiter 分隔符
   * @param strings   字符串
   * @return
   */
  public static String join(CharSequence delimiter, String... strings) {
    return String.join(delimiter, strings);
  }

  /**
   * 判断字符串是否为空
   *
   * @param cs
   * @return
   */
  public static boolean isBlank(CharSequence cs) {
    return org.apache.commons.lang3.StringUtils.isBlank(cs);
  }

  /**
   * 判断字符串是否为非空
   *
   * @param cs
   * @return
   */
  public static boolean isNotBlank(CharSequence cs) {
    return !isBlank(cs);
  }

  /**
   * 比较字符串
   * <pre>
   * StringUtils.equals(null, null)   = true
   * StringUtils.equals(null, "abc")  = false
   * StringUtils.equals("abc", null)  = false
   * StringUtils.equals("abc", "abc") = true
   * StringUtils.equals("abc", "ABC") = false
   * </pre>
   *
   * @param s1
   * @param s2
   * @return
   */
  public static boolean equals(CharSequence s1, CharSequence s2) {
    return org.apache.commons.lang3.StringUtils.equals(s1, s2);
  }
}
