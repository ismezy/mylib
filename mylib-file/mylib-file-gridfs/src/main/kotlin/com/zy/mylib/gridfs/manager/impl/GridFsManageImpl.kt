package com.zy.mylib.gridfs.manager.impl

import com.mongodb.client.gridfs.GridFSBucket
import com.mongodb.client.gridfs.model.GridFSFile
import com.zy.mylib.gridfs.manager.GridFsManage
import com.zy.mylib.utils.RandomUtils
import org.apache.commons.io.IOUtils
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.gridfs.GridFsTemplate
import org.springframework.stereotype.Component
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Named

/**
 * 文件service
 */
@Component
class GridFsManageImpl : GridFsManage {
  @Inject
  @Named("gridFsTemplate")
  lateinit var gridFsTemplate: GridFsTemplate

  @Inject
  @Named("gridFsGridFSBucket")
  lateinit var gridFSBucket: GridFSBucket

  /**
   * 保存文件
   */
  override fun save(inputStream: InputStream): GridFSFile {
    val objectId = gridFsTemplate.store(inputStream, RandomUtils.uuid())
    return gridFsTemplate.findOne(Query.query(Criteria.where("_id").`is`(objectId)))
  }

  override fun save(sourcePath: String): GridFSFile {
    return save(FileInputStream(sourcePath))
  }

  /**
   * 删除文件
   */
  override fun delete(id: String) {
    gridFsTemplate.delete(Query.query(Criteria.where("_id").`is`(id)))
  }

  override fun load(id: String, outputStream: OutputStream) {
    val file = gridFsTemplate.findOne(Query.query(Criteria.where("_id").`is`(id)))
    val bucket = gridFSBucket.openDownloadStream(file.id)
    IOUtils.copy(bucket.buffered(), outputStream)
  }

  override fun load(id: String, targetPath: String) {
    val outputStream = FileOutputStream(targetPath)
    load(id, outputStream)
    outputStream.close()
  }

  override fun findById(templateFileId: String): GridFSFile {
    return gridFsTemplate.findOne(Query.query(Criteria.where("_id").`is`(templateFileId)))
  }
}