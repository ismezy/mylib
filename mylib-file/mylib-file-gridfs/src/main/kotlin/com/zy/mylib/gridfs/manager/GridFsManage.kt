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
package com.zy.mylib.gridfs.manager

import com.mongodb.client.gridfs.model.GridFSFile
import java.io.InputStream
import java.io.OutputStream

interface GridFsManage {
  /**
   * 保存文件
   */
  fun save(inputStream: InputStream): GridFSFile

  /**
   * 保存文件
   */
  fun save(sourcePath: String): GridFSFile
  /**
   * 删除文件
   */
  fun delete(id: String)

  /**
   * 读取文件
   */
  fun load(id:String, outputStream: OutputStream)

  /**
   * 读取文件
   */
  fun load(id: String, targetPath: String)

  fun findById(templateFileId: String): GridFSFile
}