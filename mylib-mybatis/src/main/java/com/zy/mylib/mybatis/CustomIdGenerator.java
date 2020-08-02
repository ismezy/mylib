package com.zy.mylib.mybatis;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

import java.util.Random;

/**
 * 自定义id生成策略
 * @author ASUS
 */
public class CustomIdGenerator  implements IdentifierGenerator {
    Random random = new Random(System.currentTimeMillis());
    @Override
    public Long  nextId(Object entity) {
        // 当前时间加+3位随机1000以内的随机数
        return System.currentTimeMillis() * 1000 + random.nextLong() % 1000;
    }
}
