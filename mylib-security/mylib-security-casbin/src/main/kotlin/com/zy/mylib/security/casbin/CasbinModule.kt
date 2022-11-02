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
package com.zy.mylib.security.casbin

import com.zy.mylib.security.SecurityModule
import com.zy.mylib.security.casbin.adapter.MongoAdapter
import com.zy.mylib.security.casbin.manager.CasbinModelManager
import org.casbin.jcasbin.main.Enforcer
import org.casbin.jcasbin.main.SyncedEnforcer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@ComponentScan("com.zy.mylib.security.casbin")
@EnableMongoRepositories(basePackages = ["com.zy.mylib.security.casbin.dao"])
@Import(SecurityModule::class)
open class CasbinModule {
  @Value("\${system.account:default}")
  private lateinit var account: String

  @Bean
  open fun enforcer(adapter: MongoAdapter, modelManager: CasbinModelManager) : Enforcer {
    val model = modelManager.loadModel(account)
    return SyncedEnforcer(model, adapter).apply { this.enableAutoSave(true) }
  }
}