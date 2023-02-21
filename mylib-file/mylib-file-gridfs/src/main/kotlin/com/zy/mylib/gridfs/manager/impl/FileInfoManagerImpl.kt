package com.zy.mylib.gridfs.manager.impl

import com.zy.mylib.gridfs.dao.FileInfoDao
import com.zy.mylib.gridfs.entity.FileInfo
import com.zy.mylib.gridfs.manager.FileInfoManager
import com.zy.mylib.mongo.manager.impl.BaseMongoManagerImpl
import java.util.Date

import javax.inject.Named

/**
 * 文件信息 管理类
 * @author 代码生成器
 */
@Named
open class FileInfoManagerImpl: BaseMongoManagerImpl<FileInfoDao, FileInfo, String>(), FileInfoManager {
  override fun findByFullpath(fullpath: String): FileInfo? {
    return repository.findByFullpath(fullpath)
  }

  override fun findByCreateTimeAfter(date: Date): List<FileInfo> {
    return repository.findByCreateTimeGreaterThanEqual(date)
  }
}