package com.zy.mylib.sys.config.entity

import com.zy.mylib.mongo.model.BaseMongoModel
import javax.persistence.*
import org.springframework.data.mongodb.core.mapping.*

/**
 * 代码项
 * @author 代码生成器
 */
@Document("sys_codeitem")
class CodeItem: BaseMongoModel() {
  /**
   * 代码项编号
   */
  @Column
  var code: String? = null
  /**
   * 代码项名
   */
  @Column
  var caption: String? = null
  /**
   * 代码集代码
   */
  @Column
  var codemap: String? = null
  /**
   * 排序数
   */
  @Column
  var sortNum: Double? = null
  /**
   * 代码项值
   */
  @Column
  var value: String? = null
}
