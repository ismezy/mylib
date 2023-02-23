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
package com.zy.mylib.file.convert

import com.zy.mylib.file.convert.service.DocService
import com.zy.mylib.gridfs.GridFsModule
import org.apache.commons.io.IOUtils
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject

@SpringBootTest(classes = [ConvertServiceTest::class])
@Import(GridFsModule::class, FileConvertModule::class)
class ConvertServiceTest {
  @Inject
  private lateinit var docService: DocService

  @Test
  fun excel2Pdf() {
    val file = File("D:\\document\\WeChat Files\\wxid_rksat3g83imi21\\FileStorage\\File\\2023-02\\BDA0710010_《核定定额通知》.doc")
    ByteArrayOutputStream().use { os ->
      file.inputStream().use { fis ->
        IOUtils.copy(fis, os)
        docService.convertWordToPdf(os.toByteArray(), "doc", "/pdf/核定定额通知.pdf")
        docService.stop()
      }
    }
  }
}