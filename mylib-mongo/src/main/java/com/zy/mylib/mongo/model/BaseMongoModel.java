package com.zy.mylib.mongo.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.zy.mylib.base.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * mongo基础模型
 * @author 周扬
 */
@MappedSuperclass
public class BaseMongoModel implements BaseModel {
    @Id @Column(name = "_id") @JsonView(BaseView.class)
    private String id;

    /**
     * Sets new id.
     *
     * @param id New value of id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return Value of id.
     */
    public String getId() {
        return id;
    }
}
