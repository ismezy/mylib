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
package com.zy.mylib.utils

import com.google.common.base.Strings
import org.apache.commons.lang3.StringUtils
import java.util.*

/**
 * @author 扬
 * @date 2017/5/15
 */
object StringUtils {
  /**
   * 首字母大写
   *
   * @param word
   * @return 首字母大写的字符串
   */
  fun firstUpperCase(word: String): String {
    return if (Strings.isNullOrEmpty(word)) {
      word
    } else word.substring(0, 1)
      .uppercase(Locale.getDefault()) + if (word.length > 1) word.substring(1) else ""
  }

  /**
   * 首字母小写
   *
   * @param word
   * @return 首字母小写的字符串
   */
  fun firstLowerCase(word: String): String {
    return if (isBlank(word)) {
      word
    } else word.substring(0, 1)
      .lowercase(Locale.getDefault()) + if (word.length > 1) word.substring(1) else ""
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
  fun join(array: Array<String>?, delimiter: CharSequence?, start: Int, end: Int): String {
    return java.lang.String.join(delimiter, *Arrays.copyOfRange(array, start, end))
  }

  /**
   * 连接字符串
   *
   * @param array     字符串数组
   * @param delimiter 分隔符
   * @param start     数组起始位置
   * @return
   */
  fun join(array: Array<String>, delimiter: CharSequence?, start: Int): String {
    return java.lang.String.join(delimiter, *Arrays.copyOfRange(array, start, array.size))
  }

  /**
   * 连接字符串
   *
   * @param delimiter 分隔符
   * @param strings   字符串
   * @return
   */
  fun join(delimiter: CharSequence?, vararg strings: String?): String {
    return java.lang.String.join(delimiter, *strings)
  }

  /**
   * 判断字符串是否为空
   *
   * @param cs
   * @return
   */
  fun isBlank(cs: CharSequence?): Boolean {
    return StringUtils.isBlank(cs)
  }

  /**
   * 判断字符串是否为非空
   *
   * @param cs
   * @return
   */
  fun isNotBlank(cs: CharSequence?): Boolean {
    return !isBlank(cs)
  }

  /**
   * 比较字符串
   * <pre>
   * StringUtils.equals(null, null)   = true
   * StringUtils.equals(null, "abc")  = false
   * StringUtils.equals("abc", null)  = false
   * StringUtils.equals("abc", "abc") = true
   * StringUtils.equals("abc", "ABC") = false
  </pre> *
   *
   * @param s1
   * @param s2
   * @return
   */
  fun equals(s1: CharSequence?, s2: CharSequence?): Boolean {
    return StringUtils.equals(s1, s2)
  }
}
