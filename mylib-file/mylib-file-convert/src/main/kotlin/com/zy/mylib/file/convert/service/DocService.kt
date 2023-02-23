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
package com.zy.mylib.file.convert.service

import com.zy.mylib.gridfs.entity.FileInfo
import java.io.File

/**
 * @author ASUS
 */
interface DocService {
    /**
     * 转换base64文档
     *
     * @param based64
     * @param sourceFileType
     * @param targetFileType
     * @return
     */
    fun convertFromBase64(
        based64: String,
        sourceFileType: String,
        targetFileType: String
    ): String

    /**
     * 转换到临时文件夹
     *
     * @param key
     * @param sourceFileType
     * @param targetFileType
     * @return
     */
    fun convertToTmpFile(key: String, sourceFileType: String, targetFileType: String): String

    /**
     * 多个转换到临时文件夹
     *
     * @param keys
     * @return
     */
//    fun multipleConvertToTmpFile(keys: Array<String>): String

    /**
     * 转换到临时文件夹
     * @param key
     * @param sourceFileType
     * @param targetFileType
     * @return
     */
    fun convertToTmpFile(byteArray: ByteArray, sourceFileType: String, targetFileType: String): String

    /**
     * word转pdf
     * @param key
     * @param sourceFileType
     * @param targetFileType
     * @return
     */
    fun convertWordToPdf(byteArray: ByteArray, sourceFileType: String, targetPath: String): FileInfo

    /**
     * 图片转换到PDF文档
     *
     * @param imagesPath
     * @return
     */
//    fun convertImagesToPdf(imagesKeys: Array<String>): String?

    /**
     * string写入PDF文档
     *
     * @param string
     * @return
     */
//    fun convertStringToPdf(string: String): String?

  fun stop()
}
