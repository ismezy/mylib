package com.zy.mylib.system.entity;

import com.zy.mylib.data.jpa.UUIDBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 文件描述信息
 *
 * @author ASUS
 */
@Entity
@Table(name = "file_description")
public class FileDescription extends UUIDBaseEntity {
  private static final long serialVersionUID = 8517774239737709879L;
  /**
   * 对象类型
   */
  @Column(length = 32)
  private String objectType;
  /**
   * 对象ID
   */
  @Column(length = 32)
  private String objectId;
  /**
   * 文件类型
   */
  @Column(length = 32)
  private String fileType;
  /**
   * 文件扩展名
   */
  @Column(length = 8)
  private String fileExtName;
  /**
   * 排序代码
   */
  @Column
  private Integer sortNumber;
  /**
   * 文件路径
   */
  @Column(length = 512)
  private String filePath;
  /**
   * 是否删除
   */
  @Column
  private Boolean deleted;
  /**
   * 旋转角度
   */
  @Column
  private Integer rotate;
  /**
   * 自定义文件名
   */
  @Column(length = 64)
  private String customFileName;

  /**
   * Sets new 文件路径.
   *
   * @param filePath New value of 文件路径.
   */
  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  /**
   * Gets 文件类型.
   *
   * @return Value of 文件类型.
   */
  public String getFileType() {
    return fileType;
  }

  /**
   * Sets new 对象ID.
   *
   * @param objectId New value of 对象ID.
   */
  public void setObjectId(String objectId) {
    this.objectId = objectId;
  }

  /**
   * Gets 文件扩展名.
   *
   * @return Value of 文件扩展名.
   */
  public String getFileExtName() {
    return fileExtName;
  }

  /**
   * Sets new 文件类型.
   *
   * @param fileType New value of 文件类型.
   */
  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  /**
   * Gets 对象类型.
   *
   * @return Value of 对象类型.
   */
  public String getObjectType() {
    return objectType;
  }

  /**
   * Sets new 文件扩展名.
   *
   * @param fileExtName New value of 文件扩展名.
   */
  public void setFileExtName(String fileExtName) {
    this.fileExtName = fileExtName;
  }

  /**
   * Sets new 对象类型.
   *
   * @param objectType New value of 对象类型.
   */
  public void setObjectType(String objectType) {
    this.objectType = objectType;
  }

  /**
   * Gets 文件路径.
   *
   * @return Value of 文件路径.
   */
  public String getFilePath() {
    return filePath;
  }

  /**
   * Gets 对象ID.
   *
   * @return Value of 对象ID.
   */
  public String getObjectId() {
    return objectId;
  }

  /**
   * Sets new 排序代码.
   *
   * @param sortNumber New value of 排序代码.
   */
  public void setSortNumber(Integer sortNumber) {
    this.sortNumber = sortNumber;
  }

  /**
   * Gets 排序代码.
   *
   * @return Value of 排序代码.
   */
  public Integer getSortNumber() {
    return sortNumber;
  }

  /**
   * Sets new 是否删除.
   *
   * @param deleted New value of 是否删除.
   */
  public void setDeleted(Boolean deleted) {
    this.deleted = deleted;
  }

  /**
   * Gets 是否删除.
   *
   * @return Value of 是否删除.
   */
  public Boolean getDeleted() {
    return deleted;
  }

  /**
   * Sets new 旋转角度.
   *
   * @param rotate New value of 旋转角度.
   */
  public void setRotate(Integer rotate) {
    this.rotate = rotate;
  }

  /**
   * Gets 旋转角度.
   *
   * @return Value of 旋转角度.
   */
  public Integer getRotate() {
    return rotate;
  }

  /**
   * Sets new 自定义文件名.
   *
   * @param customFileName New value of 自定义文件名.
   */
  public void setCustomFileName(String customFileName) {
    this.customFileName = customFileName;
  }

  /**
   * Gets 自定义文件名.
   *
   * @return Value of 自定义文件名.
   */
  public String getCustomFileName() {
    return customFileName;
  }

  @Override
  public String description() {
    return "附件";
  }
}
