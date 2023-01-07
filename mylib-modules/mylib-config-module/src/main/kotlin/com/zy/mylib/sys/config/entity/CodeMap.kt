package com.zy.mylib.sys.config.entity

import com.zy.mylib.mongo.model.BaseMongoModel
import javax.persistence.*
import org.springframework.data.mongodb.core.mapping.*

/**
 * 代码集
 * @author 代码生成器
 */
@Document("sys_codemap")
class CodeMap: BaseMongoModel() {
  /**
   * 代码集编号
   */
  @Column
  var code: String? = null
  /**
   * 代码集名
   */
  @Column
  var caption: String? = null
}
