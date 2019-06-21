package com.zy.mylib.system.manager;

import com.zy.mylib.system.entity.ArchiveNo;
import com.zy.mylib.data.jpa.JpaManager;

/**
 * 交易流水号管理
 * @author ASUS
 */
public interface ArchiveNoManager extends JpaManager<ArchiveNo, String> {

    /**
     * 产生交易流水号
     *     车辆流水号规则8位年月日+4位流水,流水每日清零
     *     业务流水号规则8位年月日+2位类型代码+4位流水,流水每日清零
     *     批量流水号为同批次首条业务的业务流水号
     * @return 流水号
     */
    String makeArchiveNo(String type);

    /**
     * 根据流水号查询其类型
     * @param archiveNo 流水号
     * @return 类型
     */
    String getType(String archiveNo);

}
