package com.zy.mylib.webmvc.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.zy.mylib.base.model.BaseModel;

/**
 * @author 扬
 * @date 2017/1/10
 */
public class RestMessage {
  /**
   * 消息
   */
  String message;
  /**
   * 代码
   */
  String code;

  public RestMessage(String code, String message) {
    this.message = message;
    this.code = code;
  }

  /**
   * Sets new 消息.
   *
   * @param message New value of 消息.
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * Gets 代码.
   *
   * @return Value of 代码.
   */
  @JsonView(BaseModel.BaseView.class)
  public String getCode() {
    return code;
  }

  /**
   * Gets 消息.
   *
   * @return Value of 消息.
   */
  @JsonView(BaseModel.BaseView.class)
  public String getMessage() {
    return message;
  }

  /**
   * Sets new 代码.
   *
   * @param code New value of 代码.
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * 通用成功消息
   */
  public static final RestMessage SUCCESS;
  public static final RestMessage UNKNOW_ERROR;

  static {
    SUCCESS = new RestMessage("0000", "成功");
    UNKNOW_ERROR = new RestMessage("9999", "未知错误");
  }
}
