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
package com.zy.mylib.gridfs.service.impl

import com.mongodb.client.gridfs.model.GridFSFile
import com.zy.mylib.gridfs.entity.DirectoryInfo
import com.zy.mylib.gridfs.entity.FileInfo
import com.zy.mylib.gridfs.manager.DirectoryInfoManager
import com.zy.mylib.gridfs.manager.FileInfoManager
import com.zy.mylib.gridfs.manager.GridFsManage
import com.zy.mylib.gridfs.service.FileService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.File
import java.io.InputStream
import java.util.*
import javax.inject.Inject

/**
 * 文件服务
 */
@Service
open class FileServiceImpl : FileService {
  @Inject
  private lateinit var directoryInfoManager: DirectoryInfoManager
  @Inject
  private lateinit var fileInfoManager: FileInfoManager
  @Inject
  private lateinit var gridFsManager: GridFsManage

  /**
   * 创建目录
   */
  @Transactional
  override fun createDirectory(fullPath: String): DirectoryInfo {
    return createDirectory(fullPath.substringBeforeLast('/'), fullPath.substringAfterLast('/'))
  }

  /**
   * 创建目录
   */
  @Transactional
  override fun createDirectory(parent: String, name: String): DirectoryInfo {
    println("$parent, $name")
    if (parent != "/" && parent.isNotBlank()) {
      val parentDir = directoryInfoManager.findByFullPath(parent)
      if (parentDir == null) {
        createDirectory(parent.substringBeforeLast('/', "/"), parent.substringAfterLast('/'))
      }
    }
    val di = directoryInfoManager.findByFullPath("$parent/$name".replace("//", "/"))
    if(di != null) {
      return di
    }
    return directoryInfoManager.add(DirectoryInfo().apply {
      path = parent.ifBlank { "/" }
      filename = name
      createTime = Date()
      modifyTime = createTime
      fullpath = "$parent/$name".replace("//", "/")
    })
  }

  /**
   * 保存文件
   */
  @Transactional
  override fun saveFile(inputStream: InputStream, path: String): FileInfo {
    val gridFs = gridFsManager.save(inputStream)
    return saveFile(gridFs, path)
  }

  fun saveLocalFile(localPath: String, path: String): FileInfo {
    return saveFile(File(localPath).inputStream(), path)
  }

  fun saveFile(templateFileId: String, path: String): FileInfo {
    val gridFs = gridFsManager.findById(templateFileId)
    return saveFile(gridFs, path)
  }

  private fun saveFile(gridFs: GridFSFile, path: String): FileInfo {
    createDirectory(path.substringBeforeLast('/', "/"))
    val fi = fileInfoManager.findByFullpath(path)
    if(fi == null) {
      return addFile(gridFs, path)
    }
    return updateFile(fi, gridFs, path)
  }

  private fun updateFile(old: FileInfo, gridFs: GridFSFile, path: String): FileInfo {
    return fileInfoManager.update(FileInfo().apply {
      id = old.id
      fullpath = path
      filename = gridFs.objectId.toHexString()
      this.path = path.substringBeforeLast('/')
      extName = path.substringAfterLast('.', "")
      originName = path.substringAfterLast('/')
      fileSize = gridFs.length
      createTime = old.createTime
      modifyTime = Date()
    })
  }

  private fun addFile(gridFs: GridFSFile, path: String): FileInfo {
    return fileInfoManager.add(FileInfo().apply {
      fullpath = path
      filename = gridFs.objectId.toHexString()
      this.path = path.substringBeforeLast('/')
      extName = path.substringAfterLast('.', "")
      createTime = Date()
      originName = path.substringAfterLast('/')
      fileSize = gridFs.length
      modifyTime = createTime
    })
  }
}