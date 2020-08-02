package com.zy.mylib.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * mybatis 包含uuid主键实体
 * @author ASUS
 */
public class UuidBaseEntity implements Serializable {
    /**
     * uuid 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * Sets new uuid 主键.
     *
     * @param id New value of uuid 主键.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets uuid 主键.
     *
     * @return Value of uuid 主键.
     */
    public String getId() {
        return id;
    }
}
