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
package com.zy.mylib.gridfs.service

import com.zy.mylib.gridfs.entity.DirectoryInfo
import com.zy.mylib.gridfs.entity.FileInfo
import java.io.InputStream

interface FileService {

  fun createDirectory(fullPath: String): DirectoryInfo
  fun createDirectory(parent: String, name: String): DirectoryInfo
  fun saveFile(inputStream: InputStream, path: String): FileInfo
  fun saveLocalFile(localPath: String, path: String): FileInfo
  fun saveFile(tempFileId: String, path: String): FileInfo
  fun downloadStream(fileId: String): ByteArray
  fun uploadStream(byteArray: ByteArray): String
}