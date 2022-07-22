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

import com.zy.mylib.mongo.manager.impl.BaseMongoManagerImpl
import com.zy.mylib.security.casbin.dao.CasbinRuleDao
import com.zy.mylib.security.casbin.entity.CasbinRule
import com.zy.mylib.security.casbin.manager.CasbinRuleManager
import org.springframework.stereotype.Service

/**
 * 规则管理实现
 */
@Service
class CasbinRuleManagerImpl : BaseMongoManagerImpl<CasbinRuleDao, CasbinRule, String>(), CasbinRuleManager {
  override fun saveRules(rules: List<CasbinRule>): List<CasbinRule> {
    return repository.saveAll(rules);
  }

  override fun saveRule(rule: CasbinRule): CasbinRule {
    return repository.save(rule)
  }

  override fun removePolicy(ruleId: String, policy: String?) {
    var rule = findById(ruleId)
    rule?.apply { this.rules?.remove(policy) }?.let { save(it) }
  }

  override fun addPolicy(ruleId: String, policy: String?) {
    var rule = findById(ruleId)
    if(rule == null) {
      rule = CasbinRule()
      rule.id = ruleId
      rule.rules = mutableListOf()
    }
    if(!rule.rules?.contains(policy)!!) {
      save(rule.apply { policy?.let { this.rules?.add(it) } })
    }
  }
}
