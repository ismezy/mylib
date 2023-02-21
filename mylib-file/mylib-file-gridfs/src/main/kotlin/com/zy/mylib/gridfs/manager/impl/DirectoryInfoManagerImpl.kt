package com.zy.mylib.gridfs.manager.impl

import com.zy.mylib.gridfs.dao.DirectoryInfoDao
import com.zy.mylib.gridfs.entity.DirectoryInfo
import com.zy.mylib.gridfs.manager.DirectoryInfoManager
import com.zy.mylib.mongo.manager.impl.BaseMongoManagerImpl

import javax.inject.Named

/**
 * 目录信息 管理类
 * @author 代码生成器
 */
@Named
open class DirectoryInfoManagerImpl: BaseMongoManagerImpl<DirectoryInfoDao, DirectoryInfo, String>(), DirectoryInfoManager {
  override fun findByParent(parent: String): DirectoryInfo? {
    return repository.findByPath(parent)
  }

  override fun findByFullPath(fullpath: String): DirectoryInfo? {
    return repository.findByFullpath(fullpath)
  }
}