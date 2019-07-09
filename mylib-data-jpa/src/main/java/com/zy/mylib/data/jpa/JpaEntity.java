package com.zy.mylib.data.jpa;


import com.zy.mylib.base.model.BaseModel;

import javax.persistence.MappedSuperclass;

/**
 * Jpa实体基础类
 * @author ASUS
 */
@MappedSuperclass
public abstract class JpaEntity implements BaseModel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 实体描述
     * @return
     */
    public abstract String description();
}
