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
  fun loadImageProvider(fileId: String): IImageProvider {
    val byteArray = fileService.downloadStream(fileId)
    return ByteArrayImageProvider(byteArray, true)
  }
}