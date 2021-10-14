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
package com.zy.mylib.base.model;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * 基础模型
 *
 * @author ASUS
 */
@MappedSuperclass
public interface BaseModel extends Serializable {
    /**
     * JSON基础视图
     */
    interface BaseView {
    }

    /**
     * JSON列表视图
     */
    interface ListView extends BaseView {
    }

    /**
     * JSON详细视图
     */
    interface DetailView extends ListView {
    }

    /**
     * 添加验证
     */
    interface AddCheck {
    }

    /**
     * 修改验证
     */
    interface UpdateCheck {
    }

    /**
     * 实体描述
     *
     * @return
     */
    default String description() {
        EntityDescription entity = this.getClass().getAnnotation(EntityDescription.class);
        if (StringUtils.isNotBlank(entity.value())) {
            return entity.value();
        }
        throw new RuntimeException(this.getClass().toGenericString() + "未添加@EntityDescription注解");
    }
}
