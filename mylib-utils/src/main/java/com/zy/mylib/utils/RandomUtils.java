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
package com.zy.mylib.utils;

import com.google.common.base.Strings;

import java.util.Random;

/**
 * 随机数工具
 */
public class RandomUtils {

    /**
     * 生成随机数字符串，长度不足时补0
     *
     * @param len 随机数长度
     * @return
     */
    public static String randNumberString(int len) {
        return randNumberString(len, '0');
    }

    /**
     * 生成随机数字符串
     *
     * @param len     随机数长度
     * @param padChar 被位数字符
     * @return
     */
    public static String randNumberString(int len, char padChar) {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        return Strings.padStart(Integer.toString(random.nextInt((int) Math.pow(10, len))), len, padChar);
    }

    /**
     * 6位随机数字，不足时前面补0
     *
     * @return
     */
    public static String randomFor6() {
        return randNumberString(6, '0');
    }
}
