package com.zy.mylib.sys.config.entity

import com.zy.mylib.mongo.model.BaseMongoModel
import javax.persistence.*
import org.springframework.data.mongodb.core.mapping.*

/**
 * 全局配置
 * @author 代码生成器
 */
@Document("sys_global_config")
class GlobalConfig: BaseMongoModel() {
  /**
   * 配置项编号
   */
  @Column
  var code: String? = null
  /**
   * 配置项名称
   */
  @Column
  var caption: String? = null
  /**
   * 配置项值
   */
  @Column
  var value: String? = null
}
