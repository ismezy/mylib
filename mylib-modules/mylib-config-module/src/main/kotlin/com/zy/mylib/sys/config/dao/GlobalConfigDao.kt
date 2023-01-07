package com.zy.mylib.sys.config.dao

import com.zy.mylib.sys.config.entity.GlobalConfig
import com.zy.mylib.mongo.repos.BaseMongoRepository

/**
 * 全局配置 mongo dao
 * @author 代码生成器
 */
interface GlobalConfigDao : BaseMongoRepository<GlobalConfig, String> {
}