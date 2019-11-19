package com.zy.mylib.mongo.manager.impl;


import com.zy.mylib.base.i18n.I18n;
import com.zy.mylib.mongo.manager.BaseMongoManager;
import com.zy.mylib.mongo.repos.BaseMongoRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * mongodb基础管理实现类
 *
 * @param <T>
 * @param <PK>
 * @author 周扬
 */
public abstract class BaseMongoManagerImpl<T, PK extends Serializable> extends I18n implements BaseMongoManager<T, PK> {
  protected abstract BaseMongoRepository<T, PK> getDao();

  @Override
  public Optional<T> findById(PK id) {
    return getDao().findById(id);
  }

  @Override
  public List<T> findAll() {
    return (List<T>) getDao().findAll();
  }

  @Override
  public T save(T entity) {
    return getDao().save(entity);
  }

  @Override
  public Iterable<T> save(Iterable<T> entities) {
    return getDao().saveAll(entities);
  }

  @Override
  public void remove(T entity) {
    getDao().delete(entity);
  }

  @Override
  public void remove(Iterable<T> entities) {
    getDao().deleteAll(entities);
  }

  @Override
  public void remove(PK id) {
    getDao().deleteById(id);
  }

  /**
   * @param page
   */
  @Override
  public Page<T> findPage(Pageable page, Example<T> example) {
    return getDao().findAll(example, page);
  }

  @Override
  public List<T> queryList(Example<T> example) {
    return getDao().findAll(example);
  }
}
