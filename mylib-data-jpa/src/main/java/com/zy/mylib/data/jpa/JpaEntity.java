package com.zy.mylib.data.jpa;


import com.fasterxml.jackson.annotation.JsonView;
import com.zy.mylib.base.model.BaseModel;
import com.zy.mylib.utils.StringUtils;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Jpa实体基础类
 *
 * @author ASUS
 */
@MappedSuperclass
public abstract class JpaEntity implements BaseModel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 所属系统
     */
    @Column(length = 32)
    @JsonView(BaseView.class)
    String system;

    /**
     * 实体描述
     *
     * @return
     */
    public String description() {
        EntityDescription entity = this.getClass().getAnnotation(EntityDescription.class);
        if (StringUtils.isNotBlank(entity.value())) {
            return entity.value();
        }
        throw new RuntimeException(this.getClass().toGenericString() + "未添加@EntityDescription注解");
    }

    /**
     * Gets 所属系统.
     *
     * @return Value of 所属系统.
     */
    public String getSystem() {
        return system;
    }

    /**
     * Sets new 所属系统.
     *
     * @param system New value of 所属系统.
     */
    public void setSystem(String system) {
        this.system = system;
    }
}
