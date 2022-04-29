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

import org.casbin.jcasbin.persist.Helper.loadPolicyLineHandler
import java.io.BufferedReader
import java.io.IOException
import org.casbin.jcasbin.main.Enforcer
import org.casbin.jcasbin.model.Model
import org.casbin.jcasbin.persist.Adapter
import org.casbin.jcasbin.persist.Helper
import java.io.InputStreamReader
import java.io.StringReader
import java.lang.Error

/**
 * casbin adapter string impl
 *
 * @author ASUS
 */
class StringAdapter(private var policy: String) : Adapter {
  private lateinit var model: Model;
  override fun loadPolicy(model: Model) {
    this.model = model;
    loadPolicyFile(model) { line: String?, model: Model? -> Helper.loadPolicyLine(line, model) }
  }

  override fun savePolicy(model: Model) {
//    throw Error("not implemented")
  }

  override fun addPolicy(sec: String, ptype: String, rule: List<String>) {
//    throw Error("not implemented")
    policy += "$ptype, ${rule.joinToString(", ")}"
    model.addPolicy(sec, ptype, rule)
  }

  override fun removePolicy(sec: String, ptype: String, rule: List<String>) {
//    throw Error("not implemented")
  }

  override fun removeFilteredPolicy(sec: String, ptype: String, fieldIndex: Int, vararg fieldValues: String) {
//    throw Error("not implemented")
  }

  private fun loadPolicyFile(model: Model, handler: loadPolicyLineHandler<String, Model>) {
    val br = BufferedReader(StringReader(policy))
    var line = br.readLine()
    try {
      while (line != null) {
        handler.accept(line, model)
        line = br.readLine()
      }
      br.close()
    } catch (e: IOException) {
      e.printStackTrace()
      throw Error("IO error occurred")
    }
  }
}
