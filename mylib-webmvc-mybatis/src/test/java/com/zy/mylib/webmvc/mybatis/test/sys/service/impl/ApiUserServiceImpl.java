package com.zy.mylib.webmvc.mybatis.test.sys.service.impl;

import com.zy.mylib.mybatis.MyBatisBaseManagerImpl;
import com.zy.mylib.webmvc.mybatis.test.sys.entity.ApiUser;
import com.zy.mylib.webmvc.mybatis.test.sys.mapper.ApiUserMapper;
import com.zy.mylib.webmvc.mybatis.test.sys.service.IApiUserService;
import org.springframework.stereotype.Service;

import javax.inject.Named;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhouyang
 * @since 2020-07-25
 */
@Named
public class ApiUserServiceImpl extends MyBatisBaseManagerImpl<ApiUser, ApiUserMapper> implements IApiUserService {

}
