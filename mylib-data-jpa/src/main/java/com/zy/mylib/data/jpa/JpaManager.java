package com.zy.mylib.data.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * JpaManager接口定义
 * @author ASUS
 * @param <T>
 * @param <PK>
 */
public interface JpaManager<T extends JpaEntity,PK extends Serializable> {

    Optional<T> findById(PK id);

	List<T> findAll();

	T save(T entity);

	Iterable<T> save(Iterable<T> entities);

	void remove(T entity);

	void remove(Iterable<T> entities);

    void remove(PK id);

    Page<T> findPage(Pageable page, Specification<T> sepc);

    List<T> getList(Specification<T> sepc);

    T add(T entity);

    T update(T entity);
}
