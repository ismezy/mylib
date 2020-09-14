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
package com.zy.mylib.webmvc.data.jpa;

import com.fasterxml.jackson.annotation.JsonView;
import com.zy.mylib.base.exception.BusException;
import com.zy.mylib.base.model.BaseModel;
import com.zy.mylib.data.jpa.JpaEntity;
import com.zy.mylib.data.jpa.JpaManager;
import com.zy.mylib.data.jpa.PageUtils;
import com.zy.mylib.webmvc.base.BaseRest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.zy.mylib.data.jpa.PageUtils.getSpecification;

/**
 * Created by 扬 on 2017/3/10.
 */
public abstract class JpaEntityRestController<T extends JpaEntity, PK extends Serializable> extends BaseRest {
    /**
     * 获取manager
     *
     * @return
     */
    protected abstract JpaManager<T, PK> getManager();

    /**
     * 获取分页条件表达式
     *
     * @param operateMap
     * @return
     */
    protected abstract Map<String, PageUtils.Operate> getPageOperate(T entity, Map<String, PageUtils.Operate> operateMap);

    /**
     * 获取分页额外的参数
     *
     * @return
     */
    protected abstract Map<String, Object> getPageExtendParam(T entity, HttpServletRequest request, Map<String, Object> extendParams);

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @JsonView(BaseModel.DetailView.class)
    public T findOne(@PathVariable("id") PK id) {
        Optional<T> ret = getManager().findById(id);
        if (ret.isPresent()) {
            return ret.get();
        }
        throw BusException.builder().message("数据不存在").httpStatus(404).build();
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
        getManager().remove(id);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @JsonView(BaseModel.ListView.class)
    public List<T> queryList(T entity, HttpServletRequest req) {
        Map<String, PageUtils.Operate> operateMap = new HashMap<>(0);
        Map<String, Object> extendParams = new HashMap<>(0);
        Specification<T> specification = getSpecification(entity, getPageOperate(entity, operateMap), getPageExtendParam(entity, req, extendParams));
        return getManager().getList(specification);
    }

    @RequestMapping(value = "/pager", method = RequestMethod.GET)
    @JsonView(BaseModel.ListView.class)
    public Page<T> queryPager(Pageable page, T entity, HttpServletRequest req) {
        Map<String, PageUtils.Operate> operateMap = new HashMap<>(0);
        Map<String, Object> extendParams = new HashMap<>(0);
        return getManager().pager(page, entity, getPageOperate(entity, operateMap), getPageExtendParam(entity, req, extendParams));
    }
}
