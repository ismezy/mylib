package com.zy.mylib.gridfs.dao

import com.zy.mylib.gridfs.entity.DirectoryInfo
import com.zy.mylib.mongo.repos.BaseMongoRepository

/**
 * 目录信息 mongo dao
 * @author 代码生成器
 */
interface DirectoryInfoDao : BaseMongoRepository<DirectoryInfo, String> {
  fun findByPath(parent: String): DirectoryInfo?
  fun findByFullpath(fullpath: String): DirectoryInfo?
}