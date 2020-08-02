package com.zy.mylib.webmvc.mybatis.test.sys.service.impl;

import com.zy.mylib.mybatis.manager.MyBatisBaseManagerImpl;
import com.zy.mylib.webmvc.mybatis.test.sys.entity.ApiUser;
import com.zy.mylib.webmvc.mybatis.test.sys.mapper.ApiUserMapper;
import com.zy.mylib.webmvc.mybatis.test.sys.service.IApiUserService;

import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhouyang
 * @since 2020-07-25
 */
@Named
public class ApiUserServiceImpl extends MyBatisBaseManagerImpl<ApiUser, ApiUserMapper>
        implements IApiUserService {

    @Override
    protected ApiUser findExist(ApiUser entity) {
        Map<String, Object> map = new HashMap<>(0);
        map.put("code", entity.getCode());
        List<ApiUser> list = baseMapper.selectByMap(map);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public ApiUser findByCode(String code) {
        return baseMapper.findByCode(code);
    }
}
