package com.zy.mylib.system.manager.impl;

import com.zy.mylib.system.dao.ArchiveNoDao;
import com.zy.mylib.system.entity.ArchiveNo;
import com.zy.mylib.system.manager.ArchiveNoManager;
import com.zy.mylib.data.jpa.BaseJpaManager;
import com.zy.mylib.data.jpa.JpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

/**
 * @author ASUS
 */
@Service
public class ArchiveNoManagerImpl extends BaseJpaManager<ArchiveNo, String> implements ArchiveNoManager {

    @Autowired
    ArchiveNoDao archiveNoDao;


    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public String makeArchiveNo(String type) {
        if (type == null) {
            type = "";
        }
        // 计算当前日期
        Calendar now = Calendar.getInstance();
        String curDate = String.format("%04d%02d%02d", now.get(Calendar.YEAR)
                , now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH));
        // 获取市场和业务类型的记录
        List<ArchiveNo> tans = archiveNoDao.findByType(type);
        // 记录数应为0或1
        assert (tans.size() < 2);
        ArchiveNo tan = null;
        // 从未记录过则新建一条
        if (tans.size() == 0) {
            tan = new ArchiveNo();
            tan.setType(type);
            tan.setCurDate(curDate);
            tan.setCurNo(0);
        } else {    // 取已有记录
            tan = tans.get(0);
        }
        if (tan.getCurDate().equals(curDate)) { // 记录是当天的序号自增
            tan.setCurNo(tan.getCurNo() + 1);
        } else {    // 记录非当天的序号清零
            tan.setCurDate(curDate);
            tan.setCurNo(1);
        }
        archiveNoDao.save(tan);
        return String.format("%s%s%04d", curDate, type, tan.getCurNo());
    }

    @Override
    public String getType(String archiveNo) {
        if (archiveNo.length() == 16) {
            return "";
        } else if (archiveNo.length() == 18) {
            return archiveNo.substring(12, 14);
        }
        return null;
    }

    @Override
    protected JpaRepository<ArchiveNo, String> getRepository() {
        return archiveNoDao;
    }
}
