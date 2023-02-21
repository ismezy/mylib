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