package com.zy.mylib.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @author ASUS
 */
public class MyBatisBaseManagerImpl<T, M extends BaseMapper<T>> extends ServiceImpl<M, T> {
}
