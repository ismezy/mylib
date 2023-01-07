package com.zy.mylib.sys.config.manager.impl

import com.zy.mylib.sys.config.dao.CodeMapDao
import com.zy.mylib.sys.config.entity.CodeMap
import com.zy.mylib.sys.config.manager.CodeMapManager
import com.zy.mylib.mongo.manager.impl.BaseMongoManagerImpl

import javax.inject.Named

/**
 * 代码集 管理类
 * @author 代码生成器
 */
@Named
open class CodeMapManagerImpl: BaseMongoManagerImpl<CodeMapDao, CodeMap, String>(), CodeMapManager {
}