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

import com.auth0.jwt.algorithms.Algorithm;
import com.zy.mylib.base.exception.BusException;
import com.zy.mylib.security.LoginUser;
import com.zy.mylib.security.Passport;
import com.zy.mylib.security.casbin.EnforcerManager;
import org.casbin.jcasbin.main.Enforcer;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author ASUS
 */
public class AuthzFilter implements Filter {
    /**
     * enforcer manager
     */
    private EnforcerManager enforcerManager;
    /**
     * jwt token hash function
     */
    private Algorithm algorithm = Algorithm.HMAC256(Passport.HMAC_SECRET);
    /**
     * passport
     */
    private Passport<? extends LoginUser> passport;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        boolean isLogin = passport.isAuthenticated();
        LoginUser user = passport.getUser();
        Enforcer enforcer = enforcerManager.getEnforcer(user);
        if (isLogin) {
            enforcer.addRoleForUser(user.getUserId(), "user");
        }
        HttpServletRequest request = (HttpServletRequest) req;
        boolean pass = enforcer.enforce(user == null ? "guest" : user.getUserId(),
                request.getRequestURI(), request.getMethod());
        if (!pass) {
            throw BusException.builder().message(isLogin ? "未授权" : "未登录").httpStatus(isLogin ? 403 : 401).build();
        }
        chain.doFilter(req, res);
    }

    /**
     * Sets new jwt token hash function.
     *
     * @param algorithm New value of jwt token hash function.
     */
    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * Gets jwt token hash function.
     *
     * @return Value of jwt token hash function.
     */
    public Algorithm getAlgorithm() {
        return algorithm;
    }

    /**
     * Sets new enforcer manager.
     *
     * @param enforcerManager New value of enforcer manager.
     */
    public void setEnforcerManager(EnforcerManager enforcerManager) {
        this.enforcerManager = enforcerManager;
    }

    /**
     * Gets enforcer manager.
     *
     * @return Value of enforcer manager.
     */
    public EnforcerManager getEnforcerManager() {
        return enforcerManager;
    }

    /**
     * Gets passport.
     *
     * @return Value of passport.
     */
    public Passport<? extends LoginUser> getPassport() {
        return passport;
    }

    /**
     * Sets new passport.
     *
     * @param passport New value of passport.
     */
    public void setPassport(Passport<? extends LoginUser> passport) {
        this.passport = passport;
    }
}
