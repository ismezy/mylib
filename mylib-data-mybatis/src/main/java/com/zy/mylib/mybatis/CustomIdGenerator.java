/*
 * Copyright © ${project.inceptionYear} ismezy (ismezy@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
