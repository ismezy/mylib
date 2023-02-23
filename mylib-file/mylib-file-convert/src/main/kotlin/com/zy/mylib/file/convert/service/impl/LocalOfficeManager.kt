package com.zy.mylib.file.convert.service.impl

import com.zy.mylib.base.exception.BusException
import com.zy.mylib.file.convert.service.DocService
import com.zy.mylib.gridfs.entity.FileInfo
import com.zy.mylib.gridfs.manager.GridFsManage
import com.zy.mylib.gridfs.service.FileService
import org.jodconverter.core.DocumentConverter
import org.jodconverter.core.document.DefaultDocumentFormatRegistry
import org.jodconverter.core.office.OfficeException
import org.jodconverter.core.office.OfficeManager
import org.jodconverter.local.LocalConverter
import org.jodconverter.local.office.LocalOfficeManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*
import javax.inject.Inject

/**
 * @author ASUS
 */
@Service
class OpenOfficeManager : DocService {
  @Inject
  private lateinit var fileService: FileService
  @Inject
  private lateinit var gridFsManager: GridFsManage

  private var officeManager: OfficeManager? = null

  @Value("\${office.home}")
  val officeHome: String? = null

  private fun init() {
    if (officeManager == null) {
      officeManager = LocalOfficeManager.builder().officeHome(officeHome).install().build()
    }
    if (officeManager?.isRunning == false) {
      officeManager?.start()
    }
  }

  override fun stop() {
    if(officeManager != null && officeManager!!.isRunning) {
      officeManager!!.stop()
    }
  }

  override fun convertFromBase64(based64: String, sourceFileType: String, targetFileType: String): String {
    init()
    val buff = Base64.getDecoder().decode(based64)
    try {
      ByteArrayInputStream(buff).use { inputStream ->
        ByteArrayOutputStream(100 * 1024 * 1024).use { outputStream ->
          val converter: DocumentConverter = LocalConverter.builder()
              .officeManager(officeManager!!)
              .build()
          converter.convert(inputStream)
              .`as`(DefaultDocumentFormatRegistry.getFormatByExtension(sourceFileType)!!)
              .to(outputStream)
              .`as`(DefaultDocumentFormatRegistry.getFormatByExtension(targetFileType)!!)
              .execute()
          return Base64.getEncoder().encodeToString(outputStream.toByteArray())
        }
      }
    } catch (e: OfficeException) {
      e.printStackTrace()
      throw BusException.builder().message("转换失败").build()
    } catch (e: IOException) {
      e.printStackTrace()
      throw BusException.builder().message("转换失败").build()
    }
  }


  override fun convertToTmpFile(key: String, sourceFileType: String, targetFileType: String): String {
    val byteArray = fileService.downloadStream(key)
    return this.convertToTmpFile(byteArray, sourceFileType, targetFileType)
  }

  override fun convertToTmpFile(byteArray: ByteArray, sourceFileType: String, targetFileType: String): String {
    try {
      init()
      val byteArrayInputStream = ByteArrayInputStream(byteArray)
      ByteArrayOutputStream().use {
        val converter: DocumentConverter = LocalConverter.builder()
            .officeManager(officeManager!!)
            .build()
        converter.convert(byteArrayInputStream)
            .`as`(DefaultDocumentFormatRegistry.getFormatByExtension(sourceFileType)!!)
            .to(it)
            .`as`(DefaultDocumentFormatRegistry.getFormatByExtension("pdf")!!)
            .execute()
        ByteArrayInputStream(it.toByteArray()).use { it1 ->
          return gridFsManager.save(it1).objectId.toHexString()
        }
      }
    } catch (e: OfficeException) {
      e.printStackTrace()
      throw BusException.builder().message("转换失败").build()
    } catch (e: IOException) {
      e.printStackTrace()
      throw BusException.builder().message("转换失败").build()
    }
  }

  override fun convertWordToPdf(byteArray: ByteArray, sourceFileType: String, targetPath: String): FileInfo {
    try {
      init()
      val byteArrayInputStream = ByteArrayInputStream(byteArray)
      ByteArrayOutputStream().use {
        val converter: DocumentConverter = LocalConverter.builder()
            .officeManager(officeManager!!)
            .build()
        converter.convert(byteArrayInputStream)
            .`as`(DefaultDocumentFormatRegistry.getFormatByExtension(sourceFileType)!!)
            .to(it)
            .`as`(DefaultDocumentFormatRegistry.getFormatByExtension("pdf")!!)
            .execute()
        ByteArrayInputStream(it.toByteArray()).use { it1 ->
          return fileService.saveFile(it1, targetPath)
        }
      }
    } catch (e: OfficeException) {
      e.printStackTrace()
      throw BusException.builder().message("转换失败").build()
    } catch (e: IOException) {
      e.printStackTrace()
      throw BusException.builder().message("转换失败").build()
    }
  }

//  override fun multipleConvertToTmpFile(keys: Array<String>): String {
//    val inputList = arrayListOf<ByteArray>()
//    keys.forEach {
//      var byte = fileService.downloadStream(it)
//      inputList.add(byte!!)
//    }
//    val mergePDF = PdfUtils.mergePDF(inputList)
//    return fileService.uploadStream(mergePDF)
//  }

//  override fun convertImagesToPdf(imagesKeys: Array<String>): String? {
//    val images = ArrayList<ByteArray>()
//    imagesKeys.forEach {
//      assert(it != "")
//      val image = fileService.downloadStream(it)
//      images.add(image)
//    }
//    val images2Pdf = PdfUtils.Images2Pdf(images)
//    val key = fileClient.uploadStream(images2Pdf.toByteArray())
//
////        val sign = File(templatePath, "sign.pdf") //空白页，放签名
////        val files: MutableList<String> = Lists.newArrayList()
////        files.add(target.path)
////        files.add(sign.path)
////        val resultPdf = File(tempPath, UUID.randomUUID().toString() + ".pdf")
////        PdfUtils.mergePDF(files, resultPdf.path)
//    return key
//  }

//  override fun convertStringToPdf(string: StringModel): String? {
//    val pdf = PdfUtils.string2Pdf(string.string)
//    return fileClient.uploadStream(pdf.toByteArray())
//  }

}
