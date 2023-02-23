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
package com.zy.mylib.gridfs

import com.zy.mylib.gridfs.manager.FileInfoManager
import com.zy.mylib.gridfs.manager.GridFsManage
import com.zy.mylib.gridfs.service.PdfService
import com.zy.mylib.gridfs.service.impl.FileServiceImpl
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import java.util.*
import javax.inject.Inject

@SpringBootTest(classes = [GridFsManageTest::class])
@Import(GridFsModule::class)
class GridFsManageTest {
  @Inject
  lateinit var manager: GridFsManage
  @Inject
  lateinit var fileService: FileServiceImpl
  @Inject
  lateinit var fileInfoManage: FileInfoManager

  @Test
  fun saveFile() {
    var id = manager.save("E:\\work\\文档-old\\药品.zip")
    println("id: $id")
  }

  @Test
  fun saveFileInfo() {
    val fileInfo = fileService.saveLocalFile("E:\\work\\文档\\架构.png", "/images/架构.png")
    println(fileInfo.toString())
  }

  @Test
  fun findByCreateTimeGreaterThanEqual() {
    val list = fileInfoManage.findByCreateTimeAfter(Date())
    println(list.size)
  }
}