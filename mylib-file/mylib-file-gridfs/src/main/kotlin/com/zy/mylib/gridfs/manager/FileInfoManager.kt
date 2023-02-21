package com.zy.mylib.gridfs.manager

import com.zy.mylib.gridfs.entity.FileInfo
import com.zy.mylib.base.service.Manager
import java.util.*

/**
 * 文件信息 管理类接口
 * @author 代码生成器
 */
interface FileInfoManager : Manager<FileInfo, String> {
  fun findByFullpath(fullpath: String): FileInfo?
  fun findByCreateTimeAfter(date: Date): List<FileInfo>
}
