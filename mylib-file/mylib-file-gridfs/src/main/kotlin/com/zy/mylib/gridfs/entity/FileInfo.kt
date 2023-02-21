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
package com.zy.mylib.gridfs.entity

import com.zy.mylib.mongo.model.BaseMongoModel
import org.springframework.data.mongodb.core.index.Indexed
import javax.persistence.*
import org.springframework.data.mongodb.core.mapping.*
import java.util.*

/**
 * 文件信息
 * @author 代码生成器
 */
@Document("sys_file_info")
class FileInfo: BaseMongoModel() {
  /**
   * 文件全路径
   */
  @Column
  @Indexed(unique = true)
  var fullpath: String? = null
  /**
   * 文件名
   */
  @Column
  @Indexed(unique = true)
  var filename: String? = null
  /**
   * 文件扩展名
   */
  @Column
  @Indexed(unique = true)
  var extName: String? = null
  /**
   * 原文件名
   */
  @Column
  @Indexed(unique = true)
  var originName: String? = null
  /**
   * 文件所在路径
   */
  @Column
  @Indexed(unique = true)
  var path: String? = null
  /**
   * 文件创建时间
   */
  @Column
  @Indexed(unique = true)
  var createTime: Date? = null
  /**
   * 文件修改时候
   */
  @Column
  @Indexed(unique = true)
  var modifyTime: Date? = null
  /**
   * 文件大小
   */
  @Column
  @Indexed(unique = true)
  var fileSize: Long? = null
}
