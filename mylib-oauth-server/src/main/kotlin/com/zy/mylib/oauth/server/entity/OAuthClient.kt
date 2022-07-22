package com.zy.mylib.oauth.server.entity

import com.zy.mylib.mongo.model.BaseMongoModel
import javax.persistence.*
import org.springframework.data.mongodb.core.mapping.*

/**
* oauth客户端信息
* @author 代码生成器
*/
@Document("o_auth_client")
class OAuthClient: BaseMongoModel() {
    @Column(length = 255)
    var secret: String? = null
}
