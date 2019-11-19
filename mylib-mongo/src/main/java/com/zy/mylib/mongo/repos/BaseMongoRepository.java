package com.zy.mylib.mongo.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Mongodb 持久基础类
 *
 * @param <T>
 * @param <PK>
 * @author zy
 */
@NoRepositoryBean
public interface BaseMongoRepository<T, PK extends Serializable> extends MongoRepository<T, PK> {

}
