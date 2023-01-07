package com.zy.mylib.sys.config.manager.impl

import com.zy.mylib.sys.config.dao.GlobalConfigDao
import com.zy.mylib.sys.config.entity.GlobalConfig
import com.zy.mylib.sys.config.manager.GlobalConfigManager
import com.zy.mylib.mongo.manager.impl.BaseMongoManagerImpl

import javax.inject.Named

/**
 * 全局配置 管理类
 * @author 代码生成器
 */
@Named
open class GlobalConfigManagerImpl: BaseMongoManagerImpl<GlobalConfigDao, GlobalConfig, String>(), GlobalConfigManager {
}