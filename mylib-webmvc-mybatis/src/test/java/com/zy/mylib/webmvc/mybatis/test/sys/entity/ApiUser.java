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
package com.zy.mylib.webmvc.mybatis.test.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zy.mylib.mybatis.entity.UuidBaseEntity;

/**
 * <p>
 * Api用户
 * </p>
 *
 * @author zhouyang
 * @since 2020-07-25
 */
@TableName
public class ApiUser extends UuidBaseEntity {
    private static final long serialVersionUID = 3237411302995330359L;

    private String code;

    private String name;

    private String secret;

    private String system;


    /**
     * Sets new system.
     *
     * @param system New value of system.
     */
    public void setSystem(String system) {
        this.system = system;
    }

    /**
     * Gets name.
     *
     * @return Value of name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets new name.
     *
     * @param name New value of name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets new secret.
     *
     * @param secret New value of secret.
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * Gets system.
     *
     * @return Value of system.
     */
    public String getSystem() {
        return system;
    }

    /**
     * Gets code.
     *
     * @return Value of code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets new code.
     *
     * @param code New value of code.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets secret.
     *
     * @return Value of secret.
     */
    public String getSecret() {
        return secret;
    }
}
