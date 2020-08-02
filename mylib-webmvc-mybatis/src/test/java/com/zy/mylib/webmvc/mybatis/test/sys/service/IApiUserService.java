package com.zy.mylib.webmvc.mybatis.test.sys.service;

import com.zy.mylib.mybatis.manager.MyBatisBaseManager;
import com.zy.mylib.webmvc.mybatis.test.sys.entity.ApiUser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhouyang
 * @since 2020-07-25
 */
public interface IApiUserService extends MyBatisBaseManager<ApiUser> {
    /**
     * 按code查找
     * @param code
     * @return
     */
    ApiUser findByCode(String code);
}
