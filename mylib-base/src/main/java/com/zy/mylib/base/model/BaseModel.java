package com.zy.mylib.base.model;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * 基础模型
 * @author ASUS
 */
@MappedSuperclass
public interface BaseModel extends Serializable {
    interface BaseView{}
    interface ListView extends BaseView{}
    interface DetailView extends ListView{}
}
