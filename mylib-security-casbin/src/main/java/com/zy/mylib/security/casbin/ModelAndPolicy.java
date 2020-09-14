/*
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
package com.zy.mylib.security.casbin;

/**
 * casbin model and policy
 */
public class ModelAndPolicy {
    private String model;
    private String policy;

    /**
     * Gets model.
     *
     * @return Value of model.
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets new model.
     *
     * @param model New value of model.
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Sets new policy.
     *
     * @param policy New value of policy.
     */
    public void setPolicy(String policy) {
        this.policy = policy;
    }

    /**
     * Gets policy.
     *
     * @return Value of policy.
     */
    public String getPolicy() {
        return policy;
    }
}
