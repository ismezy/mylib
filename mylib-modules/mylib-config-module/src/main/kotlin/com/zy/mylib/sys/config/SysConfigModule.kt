package com.zy.mylib.sys.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@ComponentScan(
  "com.zy.mylib.sys.config.dto.mapper",
    "com.zy.mylib.sys.config.manager",
    "com.zy.mylib.sys.config.rest",
)
@EnableMongoRepositories("com.zy.mylib.sys.config.dao")
open class SysConfigModule {
}