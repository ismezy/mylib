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