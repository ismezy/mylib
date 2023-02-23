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