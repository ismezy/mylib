package com.zy.mylib.oauth.server.manager.impl

import com.zy.mylib.oauth.server.dao.OAuthClientDao
import com.zy.mylib.oauth.server.entity.OAuthClient
import com.zy.mylib.oauth.server.manager.OAuthClientManager
import com.zy.mylib.mongo.manager.impl.BaseMongoManagerImpl

import javax.inject.Named

/**
 * oauth客户端信息 管理类
 * @author 代码生成器
 */
@Named
open class OAuthClientManagerImpl: BaseMongoManagerImpl<OAuthClientDao, OAuthClient, String>(), OAuthClientManager {
}