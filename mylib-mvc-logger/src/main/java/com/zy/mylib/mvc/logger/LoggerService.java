package com.zy.mylib.mvc.logger;

import com.zy.mylib.security.LoginUser;

import java.util.Date;

/**
 * @author 扬
 * @date 2017/5/12
 */
public interface LoggerService<UT extends LoginUser> {
    /**
     * 添加日志
     *
     * @param cloudUser
     * @param logType
     * @param content
     * @param user
     * @param ip
     * @param nowTime
     */
    void addLog(String cloudUser, String targetId, String logType, String content, UT user, String ip, Date nowTime);
}
