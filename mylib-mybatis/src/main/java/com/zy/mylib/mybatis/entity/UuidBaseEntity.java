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
package com.zy.mylib.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * mybatis 包含uuid主键实体
 * @author ASUS
 */
public class UuidBaseEntity implements Serializable {
    /**
     * uuid 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * Sets new uuid 主键.
     *
     * @param id New value of uuid 主键.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets uuid 主键.
     *
     * @return Value of uuid 主键.
     */
    public String getId() {
        return id;
    }
}
