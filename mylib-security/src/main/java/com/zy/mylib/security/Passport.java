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
package com.zy.mylib.security;

import java.io.Serializable;

/**
 * @author ASUS
 */
public interface Passport<T extends Serializable> {
    /**
     * 获取当前登录用户
     * @return
     */
    T getUser();

    /**
     * 是否已登录
     * @return
     */
    boolean isAuthenticated();

    /**
     * 获取token
     * @return
     */
    String getToken();

    /**
     * 登录，返回token
     *
     * @param user
     * @return
     */
    String login(T user);

    /**
     * 登录失败
     * @param user
     */
    void onLoginFailed(String user);

    /**
     * 用户是否已锁定
     * @param user
     * @return
     */
    boolean isLock(String user);

    /**
     * 更新登录用户缓存
     * @param user
     */
    void update(T user);

    /**
     * 登出
     */
    void logout();

    /**
     * passport 类型
     *
     * @return
     */
    String getPrivoder();
}
