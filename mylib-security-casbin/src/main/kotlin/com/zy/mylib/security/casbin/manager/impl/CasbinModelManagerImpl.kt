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
package com.zy.mylib.security.casbin.manager.impl

import com.zy.mylib.security.casbin.dao.CasbinModelDao
import com.zy.mylib.security.casbin.entity.CasbinModel
import com.zy.mylib.security.casbin.manager.CasbinModelManager
import com.zy.mylib.mongo.manager.impl.BaseMongoManagerImpl
import org.casbin.jcasbin.model.Model
import java.io.InputStreamReader

import javax.inject.Named

/**
 * casbin model 管理类
 * @author 代码生成器
 */
@Named
open class CasbinModelManagerImpl : BaseMongoManagerImpl<CasbinModelDao, CasbinModel, String>(), CasbinModelManager {
  override fun loadModel(account: String): Model {
    val m = findById(account)
    return Model().apply {
      if(m == null || m.model.isNullOrBlank()) {
        // 如果数据库无model数据则从资源中获取默认model，并写入mongo
        ClassLoader.getSystemResourceAsStream("model.default.conf").use {
          val modelString = InputStreamReader(it).readText()
          this.loadModelFromText(modelString)
          saveModel(account, modelString)
        }
      } else {
        this.loadModelFromText(m.model)
      }
    }
  }

  private fun saveModel(account: String, modelString: String): CasbinModel {
    return save(CasbinModel().apply {
      this.id = account
      this.model = modelString
    })
  }
}