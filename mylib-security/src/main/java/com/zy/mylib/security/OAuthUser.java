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
package com.zy.mylib.security;

/**
 * oauth user
 *
 * @author ASUS
 */
public class OAuthUser {
    /**
     * 用户服务提供方唯一用户id
     */
    private String id;
    /**
     * 用户提供方
     */
    private String provider;

    /**
     * Sets new 用户提供方.
     *
     * @param provider New value of 用户提供方.
     */
    public void setProvider(String provider) {
        this.provider = provider;
    }

    /**
     * Sets new 用户服务提供方唯一用户id.
     *
     * @param id New value of 用户服务提供方唯一用户id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets 用户提供方.
     *
     * @return Value of 用户提供方.
     */
    public String getProvider() {
        return provider;
    }

    /**
     * Gets 用户服务提供方唯一用户id.
     *
     * @return Value of 用户服务提供方唯一用户id.
     */
    public String getId() {
        return id;
    }
}
