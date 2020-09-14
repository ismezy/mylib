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
package com.zy.mylib.mybatis.manager;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author ASUS
 */
public interface MyBatisBaseManager<T> extends IService<T> {
    /**
     * 新增
     * @param entity
     * @return
     */
    T add(T entity);

    /**
     * 更新
     * @param entity
     * @return
     */
    T update(T entity);
}
