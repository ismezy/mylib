package com.zy.mylib.webmvc.mybatis.test.sys.mapper;

import com.zy.mylib.mybatis.mapper.MyBatisBaseMapper;
import com.zy.mylib.webmvc.mybatis.test.sys.entity.ApiUser;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhouyang
 * @since 2020-07-25
 */
public interface ApiUserMapper extends MyBatisBaseMapper<ApiUser> {
    ApiUser findByCode(@Param("code") String code);
}
