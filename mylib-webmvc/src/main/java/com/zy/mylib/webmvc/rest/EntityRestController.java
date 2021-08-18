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
package com.zy.mylib.webmvc.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.zy.mylib.base.exception.BusException;
import com.zy.mylib.base.model.BaseModel;
import com.zy.mylib.base.model.Condition;
import com.zy.mylib.base.model.PageResponse;
import com.zy.mylib.base.service.Manager;
import com.zy.mylib.webmvc.base.BaseRest;
import com.zy.mylib.webmvc.model.QueryWrapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 扬 on 2017/3/10.
 */
public abstract class EntityRestController<T extends BaseModel, PK extends Serializable> extends BaseRest {
    /**
     * 获取manager
     *
     * @return
     */
    protected abstract Manager<T, PK> getManager();

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @JsonView(BaseModel.DetailView.class)
    public T findOne(@PathVariable("id") PK id) {
       return getManager().findById(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @JsonView(BaseModel.DetailView.class)
    public T addEntity(@Validated(BaseModel.AddCheck.class) @RequestBody T entity) {
        return addEntityImpl(entity);
    }

    protected T addEntityImpl(T entity) {
        return getManager().add(entity);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @JsonView(BaseModel.DetailView.class)
    public T updateEntity(@Validated(BaseModel.UpdateCheck.class) @RequestBody T entity) {
        return updateEntityImpl(entity);
    }

    protected T updateEntityImpl(T entity) {
        return getManager().update(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeEntity(@PathVariable("id") PK id) {
        getManager().delete(id);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @JsonView(BaseModel.ListView.class)
    public List<T> queryList(QueryWrapper queryWrapper) {
        return getManager().findList(queryWrapper.getConditions(), queryWrapper.getSorts());
    }

    @RequestMapping(value = "/pager", method = RequestMethod.GET)
    @JsonView(BaseModel.ListView.class)
    public PageResponse<T> queryPager(QueryWrapper queryWrapper) {
        return getManager().pageQuery(queryWrapper.getPage(), queryWrapper.getConditions());
    }
}