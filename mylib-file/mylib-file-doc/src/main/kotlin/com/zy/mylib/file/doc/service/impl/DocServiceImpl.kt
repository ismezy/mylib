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
package com.zy.mylib.file.doc.service.impl

import com.zy.mylib.file.doc.service.DocService
import com.zy.mylib.gridfs.service.FileService
import fr.opensagres.xdocreport.document.images.ByteArrayImageProvider
import fr.opensagres.xdocreport.document.images.IImageProvider
import org.springframework.stereotype.Service
import javax.inject.Inject

@Service
class DocServiceImpl : DocService {
  @Inject
  private lateinit var fileService: FileService
  /**
   * 获取图片，返回模板ImageProvider
   */
  override fun loadImageProvider(fileId: String): IImageProvider {
    val byteArray = fileService.downloadStream(fileId)
    return ByteArrayImageProvider(byteArray, true)
  }
}