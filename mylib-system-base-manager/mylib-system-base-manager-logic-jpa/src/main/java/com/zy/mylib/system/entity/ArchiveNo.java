package com.zy.mylib.system.entity;

import com.zy.mylib.data.jpa.UUIDBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * 交易流水号产生表-用于产生车辆流水号以及批次流水号
 *
 * @author ASUS
 */
@Entity
@Table(name = "archive_no")
public class ArchiveNo extends UUIDBaseEntity {

    private static final long serialVersionUID = -6928933042199706019L;

    /**
     * 流水类型 null|2位
     */
    @Column(length = 2)
    private String type;

    /**
     * 当前日期
     */
    @Column(length = 8)
    private String curDate;

    /**
     * 当前顺序号
     */
    @Column
    private Integer curNo;
    @Version
    @Column
    private Integer version;

    /**
     * Gets 当前顺序号.
     *
     * @return Value of 当前顺序号.
     */
    public Integer getCurNo() {
        return curNo;
    }

    /**
     * Gets 当前日期.
     *
     * @return Value of 当前日期.
     */
    public String getCurDate() {
        return curDate;
    }

    /**
     * Sets new 当前日期.
     *
     * @param curDate New value of 当前日期.
     */
    public void setCurDate(String curDate) {
        this.curDate = curDate;
    }

    /**
     * Sets new 当前顺序号.
     *
     * @param curNo New value of 当前顺序号.
     */
    public void setCurNo(Integer curNo) {
        this.curNo = curNo;
    }

    /**
     * Sets new 流水类型 null|2位.
     *
     * @param type New value of 流水类型 null|2位.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets 流水类型 null|2位.
     *
     * @return Value of 流水类型 null|2位.
     */
    public String getType() {
        return type;
    }

    /**
     * Gets version.
     *
     * @return Value of version.
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * Sets new version.
     *
     * @param version New value of version.
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String description() {
        return "流水号";
    }
}
