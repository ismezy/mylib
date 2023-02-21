package com.zy.mylib.gridfs.service

import com.zy.mylib.gridfs.entity.DirectoryInfo
import com.zy.mylib.gridfs.entity.FileInfo
import java.io.InputStream

interface FileService {

  @Transactional
  fun createDirectory(fullPath: String): DirectoryInfo
  @Transactional
  fun createDirectory(parent: String, name: String): DirectoryInfo
  @Transactional
  fun saveFile(inputStream: InputStream, path: String): FileInfo
}