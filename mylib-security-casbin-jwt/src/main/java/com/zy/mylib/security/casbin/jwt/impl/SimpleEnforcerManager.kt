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
package com.zy.mylib.security.casbin.jwt.impl;

import com.zy.mylib.security.LoginUser;
import com.zy.mylib.security.casbin.StringAdapter;
import com.zy.mylib.security.casbin.StringModel;
import com.zy.mylib.security.casbin.EnforcerManager;
import com.zy.mylib.security.casbin.ModelAndPolicy;
import com.zy.mylib.security.casbin.UserAuthzService;
import org.casbin.jcasbin.main.Enforcer;

/**
 * enforcer 管理基础实现
 */
public class SimpleEnforcerManager implements EnforcerManager<LoginUser> {
    /**
     * 用户权限服务
     */
    private UserAuthzService<LoginUser> userAuthzService;

    @Override
    public Enforcer getEnforcer(LoginUser user) {
        return userAuthzService.getEnforcer(user);
    }

    /**
     * Sets new 用户权限服务.
     *
     * @param userAuthzService New value of 用户权限服务.
     */
    public void setUserAuthzService(UserAuthzService userAuthzService) {
        this.userAuthzService = userAuthzService;
    }

    /**
     * Gets 用户权限服务.
     *
     * @return Value of 用户权限服务.
     */
    public UserAuthzService getUserAuthzService() {
        return userAuthzService;
    }
}
