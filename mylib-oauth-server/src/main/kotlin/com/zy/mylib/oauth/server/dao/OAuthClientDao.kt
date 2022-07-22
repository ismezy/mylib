package com.zy.mylib.oauth.server.dao

import com.zy.mylib.oauth.server.entity.OAuthClient
import com.zy.mylib.mongo.repos.BaseMongoRepository

/**
 * oauth客户端信息 mongo dao
 * @author 代码生成器
 */
interface OAuthClientDao : BaseMongoRepository<OAuthClient, String> {
}