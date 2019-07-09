package com.zy.mylib.system.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.zy.mylib.data.jpa.NameCodeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 系统配置项
 * @author ASUS
 */
@Entity
@Table(name ="sys_config_item")
public class SysConfigItem extends NameCodeEntity {
    private static final long serialVersionUID = 2584129097724801397L;
    /**
     * 配置项值
     */
    @Column(name = "value") @Lob
    @JsonView(BaseView.class)
    private String value;

    /**
     * Gets 配置项值.
     *
     * @return Value of 配置项值.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets new 配置项值.
     *
     * @param value New value of 配置项值.
     */
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String description() {
        return "系统配置项";
    }
}
