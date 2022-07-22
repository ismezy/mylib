package com.zy.mylib.oauth.server

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@ComponentScan("com.zy.mylib.oauth.server")
@EnableMongoRepositories("com.zy.mylib.oauth.server")
open class OAuthServerModule {
}