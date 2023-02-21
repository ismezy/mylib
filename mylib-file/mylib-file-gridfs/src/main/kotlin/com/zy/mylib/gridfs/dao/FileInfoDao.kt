package com.zy.mylib.gridfs.dao

import com.zy.mylib.gridfs.entity.FileInfo
import com.zy.mylib.mongo.repos.BaseMongoRepository
import java.util.*

/**
 * 文件信息 mongo dao
 * @author 代码生成器
 */
interface FileInfoDao : BaseMongoRepository<FileInfo, String> {
  fun findByFullpath(fullpath: String): FileInfo?
  fun findByCreateTimeGreaterThanEqual(date: Date): List<FileInfo>
}