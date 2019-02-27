package com.zy.mylib.data.jpa;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 * 基础DAO接口
 * @author 扬
 *
 */
@NoRepositoryBean
public interface JpaRepository<T,PK extends Serializable> extends PagingAndSortingRepository<T, PK>,
        JpaSpecificationExecutor<T> {
}
