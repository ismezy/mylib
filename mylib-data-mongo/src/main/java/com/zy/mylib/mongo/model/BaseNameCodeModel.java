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
package com.zy.mylib.mongo.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 带名称和编号的基础模型
 *
 * @author ASUS
 */
@MappedSuperclass
public class BaseNameCodeModel extends BaseMongoModel {
    private static final long serialVersionUID = 6599338697014255001L;
    /**
     * 名称
     */
    @Column
    @Indexed
    @JsonView(ListView.class)
    private String name;
    /**
     * 编号
     */
    @Column
    @Indexed
    @JsonView(ListView.class)
    private String code;

    /**
     * Sets new 编号.
     *
     * @param code New value of 编号.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Sets new 名称.
     *
     * @param name New value of 名称.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets 编号.
     *
     * @return Value of 编号.
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets 名称.
     *
     * @return Value of 名称.
     */
    public String getName() {
        return name;
    }
}
