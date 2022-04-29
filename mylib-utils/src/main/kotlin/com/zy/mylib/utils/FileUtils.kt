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

import com.google.common.io.Files
import org.apache.commons.io.FileUtils
import org.apache.commons.io.FilenameUtils
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

/**
 * 文件工具类
 *
 * @author ASUS
 */
object FileUtils {
  /**
   *
   *
   * 获取文件扩展名
   *
   * <pre>
   * 例子
   * foo.txt      --&gt; "txt"
   * a/b/c.jpg    --&gt; "jpg"
   * a/b.txt/c    --&gt; ""
   * a/b/c        --&gt; ""
  </pre> *
   *
   * @param fileName
   */
  fun getFileExtName(fileName: String?): String {
    return FilenameUtils.getExtension(fileName)
  }

  /**
   * 复制文件，如果目标目录不存在则创建目录
   *
   * @param from
   * @param to
   * @throws IOException
   */
  @Throws(IOException::class)
  fun copyAndCreatePath(from: File?, to: File?) {
    Files.createParentDirs(to)
    Files.copy(from, to)
  }

  /**
   * 复制文件，如果目标目录不存在则创建目录
   *
   * @param from
   * @param to
   * @throws IOException
   */
  @Throws(IOException::class)
  fun copyAndCreatePath(from: InputStream?, to: File?) {
    Files.createParentDirs(to)
    FileUtils.copyToFile(from, to)
  }

  /**
   * 根据文件扩展名返回HTTP正文类型
   *
   * @param fileExt
   * @return
   */
  fun getContentType(fileExt: String): String {
    if ("jpg" == fileExt) {
      return "image/jpeg"
    } else if ("png" == fileExt) {
      return "image/png"
    } else if ("pdf" == fileExt) {
      return "application/pdf"
    } else if ("doc" == fileExt || "docx" == fileExt) {
      return "application/msword"
    }
    return "application/octet-stream"
  }

  /**
   * 文件复制
   *
   * @param from
   * @param to
   * @throws IOException
   */
  @Throws(IOException::class)
  fun copy(from: File?, to: OutputStream?) {
    Files.copy(from, to)
  }

  /**
   * 文件复制
   *
   * @param from
   * @param to
   * @throws IOException
   */
  @Throws(IOException::class)
  fun copy(from: File?, to: File?) {
    Files.copy(from, to)
  }

  /**
   * 获取文件名
   *
   * @param filePath
   * @return
   */
  fun getFilename(filePath: String?): String {
    return FilenameUtils.getName(filePath)
  }

  @Throws(IOException::class)
  fun readAllText(`is`: InputStream): String {
    val sb = StringBuffer(1024 * 3)
    var len = 0
    val buffLen = 1024
    val buff = ByteArray(buffLen)
    do {
      len = `is`.read(buff, 0, buffLen)
      sb.append(String(buff, 0, len, Charsets.UTF_8))
    } while (len == buffLen)
    `is`.close()
    return sb.toString()
  }
}
