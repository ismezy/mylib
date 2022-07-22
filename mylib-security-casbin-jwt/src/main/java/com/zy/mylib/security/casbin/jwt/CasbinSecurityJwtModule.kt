/*
 * Copyright © 2020 ismezy (ismezy@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zy.mylib.security.casbin.jwt

import com.zy.mylib.security.Passport
import com.zy.mylib.security.casbin.CasbinModule
import com.zy.mylib.security.casbin.jwt.impl.AuthzFilter
import org.casbin.jcasbin.main.Enforcer
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisOperations
import org.springframework.data.redis.core.RedisTemplate
import java.io.Serializable
import javax.inject.Named

/**
 * casbin 安全配置
 */
@Configuration
@Import(CasbinModule::class)
open class CasbinSecurityJwtModule {
  @Bean
  @ConditionalOnMissingBean(AuthzFilter::class)
  open fun authzFilter(enforcer: Enforcer, passport: Passport,
                       @Named("jwtTokenRedisOperations") redisOperations: RedisOperations<String, Serializable>): AuthzFilter {
    return AuthzFilter().apply {
      this.enforcer = enforcer
      this.passport = passport
    }
  }

  @Bean("jwtTokenRedisOperations")
  open fun jwtTokenRedisOperations(connectionFactory: RedisConnectionFactory): RedisOperations<String, Serializable> {
    return RedisTemplate<String, Serializable>().apply {
      this.connectionFactory = connectionFactory
    }
  }
}