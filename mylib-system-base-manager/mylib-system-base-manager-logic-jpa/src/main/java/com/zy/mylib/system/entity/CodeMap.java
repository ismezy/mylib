package com.zy.mylib.system.entity;

import com.zy.mylib.data.jpa.NameCodeEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author ASUS
 */
@Entity
@Table(name = "sys_codemap")
public class CodeMap extends NameCodeEntity {
  private static final long serialVersionUID = -3532091040124604358L;

  @Override
  public String description() {
    return "字典分类";
  }
}
