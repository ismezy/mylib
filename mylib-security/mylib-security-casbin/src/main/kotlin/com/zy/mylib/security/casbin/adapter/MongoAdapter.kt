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
package com.zy.mylib.security.casbin.adapter

import com.zy.mylib.security.casbin.entity.CasbinRule
import com.zy.mylib.security.casbin.manager.CasbinRuleManager
import org.casbin.jcasbin.model.Model
import org.casbin.jcasbin.persist.Adapter
import org.casbin.jcasbin.persist.Helper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.regex.Pattern
import javax.inject.Inject

@Component
open class MongoAdapter : Adapter {
  @Value("\${system.account:default}")
  private lateinit var account: String

  @Inject
  private lateinit var casbinRuleManager: CasbinRuleManager

  override fun loadPolicy(model: Model?) {
    val rule = casbinRuleManager.findById(account)
    rule?.rules?.forEach {
      Helper.loadPolicyLine(it, model)
    }
  }

  override fun savePolicy(model: Model?) {
    val policy = model?.model?.values?.flatMap { sec ->
      sec.values.flatMap { ptype ->
        ptype.policy.map { it ->
          it.joinToString()
        }
      }
    }
    casbinRuleManager.saveRule(CasbinRule().apply {
      this.id = account
      this.rules = policy as MutableList<String>?
    })
  }

  override fun addPolicy(sec: String?, ptype: String?, rule: MutableList<String>?) {
    casbinRuleManager.addPolicy(account, rule?.joinToString());
  }

  override fun removePolicy(sec: String?, ptype: String?, rule: MutableList<String>?) {
    casbinRuleManager.removePolicy(account, rule?.joinToString());
  }

  override fun removeFilteredPolicy(sec: String?, ptype: String?, fieldIndex: Int, vararg fieldValues: String?) {
    val rule = casbinRuleManager.findById(account) ?: return

    var pattern = Pattern.compile("^$ptype,\\s?(\\.+,\\s?){$fieldIndex}${fieldValues.joinToString()}\$")
    println(pattern)
    rule.rules = rule.rules?.filter { !pattern.matcher(it).find() } as MutableList<String>?
    casbinRuleManager.saveRule(rule)
  }
}
