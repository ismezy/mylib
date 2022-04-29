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
package com.zy.mylib.utils

import java.io.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

/**
 * @author 许乐
 * @date 2017/3/2
 */
object HashUtils {
  @Throws(Exception::class)
  fun getHash(file: File?): String {
    var b = ByteArray(0)
    b = createChecksum(file)
    var result = ""
    for (i in b.indices) {
      //加0x100是因为有的b[i]的十六进制只有1位
      result += Integer.toString((b[i].toInt() and 0xff) + 0x100, 16).substring(1)
    }
    return result
  }

  @Throws(IOException::class, NoSuchAlgorithmException::class)
  fun sha256(content: String): ByteArray {
    val buff = content.toByteArray(charset("utf-8"))
    val byteInputStream = ByteArrayInputStream(buff)
    return createChecksum(byteInputStream, "SHA-256")
  }

  fun toBase64String(buff: ByteArray?): String {
    return Base64.getEncoder().encode(buff).toString()
  }

  @Throws(IOException::class, NoSuchAlgorithmException::class)
  fun createChecksum(`is`: InputStream, algorithm: String?): ByteArray {
    //如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256
    val bis = BufferedInputStream(`is`)
    val complete = MessageDigest.getInstance(algorithm)
    var numRead: Int
    val buff = ByteArray(1024)
    do {
      //从文件读到buffer，最多装满buffer
      numRead = bis.read(buff)
      if (numRead > 0) {
        //用读到的字节进行MD5的计算，第二个参数是偏移量
        complete.update(buff, 0, numRead)
      }
    } while (numRead != -1)
    bis.close()
    `is`.close()
    return complete.digest()
  }

  @JvmOverloads
  @Throws(IOException::class, NoSuchAlgorithmException::class)
  fun createChecksum(file: File?, algorithm: String? = "MD5"): ByteArray {
    val fis: InputStream = FileInputStream(file)
    return createChecksum(fis, algorithm)
  }

  /**
   * 用于获取一个String的md5值
   *
   * @param str
   * @return
   */
  @Throws(Exception::class)
  fun getMd5(str: String): String {
    val md5 = MessageDigest.getInstance("MD5")
    val bs = md5.digest(str.toByteArray())
    val sb = StringBuilder(40)
    for (x in bs) {
      if (x.toInt() and 0xff shr 4 == 0) {
        sb.append("0").append(Integer.toHexString(x.toInt() and 0xff))
      } else {
        sb.append(Integer.toHexString(x.toInt() and 0xff))
      }
    }
    return sb.toString()
  }
}
