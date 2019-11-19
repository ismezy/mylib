package com.zy.mylib.base.model;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * 基础模型
 *
 * @author ASUS
 */
@MappedSuperclass
public interface BaseModel extends Serializable {
  /**
   * JSON基础视图
   */
  interface BaseView {
  }

  /**
   * JSON列表视图
   */
  interface ListView extends BaseView {
  }

  /**
   * JSON详细视图
   */
  interface DetailView extends ListView {
  }

  /**
   * 添加验证
   */
  interface AddCheck {
  }

  /**
   * 修改验证
   */
  interface UpdateCheck {
  }
}
