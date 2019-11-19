package com.zy.mylib.data.jpa;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author 扬
 * @date 2017/3/23
 */
@MappedSuperclass
public abstract class NameCodeEntity extends UUIDBaseEntity {
  @JsonView(BaseView.class)
  @Column(length = 64)
  private String name;
  @JsonView(BaseView.class)
  @Column(length = 64)
  private String code;

  /**
   * Gets name.
   *
   * @return Value of name.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets code.
   *
   * @return Value of code.
   */
  public String getCode() {
    return code;
  }

  /**
   * Sets new code.
   *
   * @param code New value of code.
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * Sets new name.
   *
   * @param name New value of name.
   */
  public void setName(String name) {
    this.name = name;
  }
}
