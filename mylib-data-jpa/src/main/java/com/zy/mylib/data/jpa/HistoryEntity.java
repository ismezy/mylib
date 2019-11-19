package com.zy.mylib.data.jpa;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author 扬
 * @date 2017/5/15
 */
@MappedSuperclass
public abstract class HistoryEntity extends UUIDBaseEntity {
  public interface HistoryView {
  }

  /**
   * 创建人
   */
  @Column(length = 32)
  @JsonView(HistoryView.class)
  private String createUserId;

  /**
   * 创建时间
   */
  @Column
  @JsonView(HistoryView.class)
  private Date createTime;
  /**
   * 最后修改时间
   */
  @Column
  @JsonView(HistoryView.class)
  private Date lastModifyTime;
  /**
   * 最后修改人
   */
  @Column(length = 32)
  @JsonView(HistoryView.class)
  private String lastModifyUserId;

  /**
   * Gets 创建人.
   *
   * @return Value of 创建人.
   */
  public String getCreateUserId() {
    return createUserId;
  }

  /**
   * Sets new 创建人.
   *
   * @param createUserId New value of 创建人.
   */
  public void setCreateUserId(String createUserId) {
    this.createUserId = createUserId;
  }

  /**
   * Gets 最后修改人.
   *
   * @return Value of 最后修改人.
   */
  public String getLastModifyUserId() {
    return lastModifyUserId;
  }

  /**
   * Sets new 最后修改人.
   *
   * @param lastModifyUserId New value of 最后修改人.
   */
  public void setLastModifyUserId(String lastModifyUserId) {
    this.lastModifyUserId = lastModifyUserId;
  }

  /**
   * Sets new 创建时间.
   *
   * @param createTime New value of 创建时间.
   */
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  /**
   * Gets 创建时间.
   *
   * @return Value of 创建时间.
   */
  public Date getCreateTime() {
    return createTime;
  }

  /**
   * Gets 最后修改时间.
   *
   * @return Value of 最后修改时间.
   */
  public Date getLastModifyTime() {
    return lastModifyTime;
  }

  /**
   * Sets new 最后修改时间.
   *
   * @param lastModifyTime New value of 最后修改时间.
   */
  public void setLastModifyTime(Date lastModifyTime) {
    this.lastModifyTime = lastModifyTime;
  }
}
