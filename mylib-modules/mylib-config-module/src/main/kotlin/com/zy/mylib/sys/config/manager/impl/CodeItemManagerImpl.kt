package com.zy.mylib.sys.config.manager.impl

import com.zy.mylib.sys.config.dao.CodeItemDao
import com.zy.mylib.sys.config.entity.CodeItem
import com.zy.mylib.sys.config.manager.CodeItemManager
import com.zy.mylib.mongo.manager.impl.BaseMongoManagerImpl

import javax.inject.Named

/**
 * 代码项 管理类
 * @author 代码生成器
 */
@Named
open class CodeItemManagerImpl: BaseMongoManagerImpl<CodeItemDao, CodeItem, String>(), CodeItemManager {
}