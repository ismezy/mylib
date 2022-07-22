package com.zy.mylib.security

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@ComponentScan("com.zy.mylib.security.manager", "com.zy.mylib.security.provider")
@EnableMongoRepositories("com.zy.mylib.security.dao")
open class SecurityModule {
}