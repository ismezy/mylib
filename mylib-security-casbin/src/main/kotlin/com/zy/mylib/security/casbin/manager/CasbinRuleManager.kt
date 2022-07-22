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
package com.zy.mylib.security.casbin.manager

import com.zy.mylib.base.service.Manager
import com.zy.mylib.security.casbin.entity.CasbinRule

/**
 * 规则管理
 */
interface CasbinRuleManager : Manager<CasbinRule, String> {
  fun saveRules(rules: List<CasbinRule>): List<CasbinRule>
  fun saveRule(rule: CasbinRule): CasbinRule
  fun addPolicy(ruleId: String, policy: String?)
  fun removePolicy(ruleId: String, policy: String?)
}
