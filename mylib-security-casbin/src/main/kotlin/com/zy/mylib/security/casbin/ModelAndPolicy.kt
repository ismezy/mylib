/**
 * Copyright © ${project.inceptionYear} ismezy (ismezy@qq.com)
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
import java.io.InputStreamReader

/**
 * casbin model and policy
 */
class ModelAndPolicy {
  /**
   * Gets model.
   *
   * @return Value of model.
   */
  /**
   * Sets new model.
   *
   * @param model New value of model.
   */
  var model: String? = null
  /**
   * Gets policy.
   *
   * @return Value of policy.
   */
  /**
   * Sets new policy.
   *
   * @param policy New value of policy.
   */
  var policy: String? = null
}