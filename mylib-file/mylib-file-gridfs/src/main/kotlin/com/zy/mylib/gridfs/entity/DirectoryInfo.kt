package com.zy.mylib.gridfs.entity

import com.zy.mylib.mongo.model.BaseMongoModel
import org.springframework.data.mongodb.core.index.Indexed
import javax.persistence.*
import org.springframework.data.mongodb.core.mapping.*
import java.util.*

/**
 * 目录信息
 * @author 代码生成器
 */
@Document("sys_directory_info")
class DirectoryInfo: BaseMongoModel() {
  /**
   * 目录全路径
   */
  @Column
  @Indexed(unique = true)
  var fullpath: String? = null
  /**
   * 目录名
   */
  @Column
  @Indexed(unique = true)
  var filename: String? = null
  /**
   * 目录所在路径
   */
  @Column
  @Indexed(unique = true)
  var path: String? = null
  /**
   * 目录创建时间
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
   * 目录下所有文件大小
   */
  @Column
  @Indexed(unique = true)
  var directorySize: Long? = null
}
