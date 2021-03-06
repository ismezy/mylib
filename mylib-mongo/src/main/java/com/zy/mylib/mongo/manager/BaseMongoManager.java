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
package com.zy.mylib.mongo.manager;

import com.zy.mylib.base.exception.BusException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * mongodb基础管理接口
 *
 * @param <T>
 * @param <PK>
 * @author 周扬
 */
public interface BaseMongoManager<T, PK extends Serializable> {
    Optional<T> findById(PK id);

    List<T> findAll();

    Page<T> findPage(Pageable page, Example<T> example);

    List<T> queryList(Example<T> example);

    T save(T entity);

    Iterable<T> save(Iterable<T> entities);

    void remove(T entity);

    void remove(Iterable<T> entities);

    void remove(PK id);

    /**
     * 返回异常
     *
     * @param messageTemplate 消息模板
     * @param args            模板参数
     * @return
     */
    BusException newBusException(String messageTemplate, Object... args);
}
