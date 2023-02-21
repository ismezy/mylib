package com.zy.mylib.gridfs.manager

import com.zy.mylib.gridfs.entity.DirectoryInfo
import com.zy.mylib.base.service.Manager

/**
 * 目录信息 管理类接口
 * @author 代码生成器
 */
interface DirectoryInfoManager : Manager<DirectoryInfo, String> {
  fun findByParent(parent: String): DirectoryInfo?
  fun findByFullPath(fullpath: String): DirectoryInfo?
}
