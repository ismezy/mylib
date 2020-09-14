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
package com.zy.mylib.security.casbin.zuul.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.zy.mylib.base.exception.BusException;
import com.zy.mylib.security.casbin.EnforcerManager;
import com.zy.mylib.utils.StringUtils;
import org.casbin.jcasbin.main.Enforcer;

import javax.servlet.http.HttpServletRequest;

/**
 * zuul 授权控制filter
 *
 * @author ASUS
 */
public class ZuulAuthzFilter extends ZuulFilter {
    /**
     * enforcer manager
     */
    private EnforcerManager enforcerManager;
    /**
     * 启用
     */
    private boolean enabled = true;
    private Algorithm algorithm = Algorithm.HMAC256("dduptop.com");

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return this.enabled;
    }

    @Override
    public Object run() throws ZuulException {
//        Model model = new Model();
//        try {
//            model.loadModelFromText(FileUtils.readAllText(modelStream));
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new ZuulException(e, "读取model失败", 500, e.getCause().toString());
//        }
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getHeader("token");
        String user = "anon";
        boolean isLogin = false;
        if (!StringUtils.isBlank(token)) {
            try {
                DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
                user = jwt.getSubject();
                isLogin = true;
            } catch (JWTVerificationException e) {
                e.printStackTrace();
            }
        }
        Enforcer enforcer = this.getEnforcerManager().getEnforcer(user);
        enforcer.enableLog(true);
        boolean pass = enforcer.enforce(user, request.getRequestURI(), request.getMethod());
        if (!pass) {
            throw BusException.builder().message(isLogin ? "未授权" : "未登录").httpStatus(isLogin ? 403 : 401).build();
        }
        return null;
    }

    /**
     * Sets new 启用.
     *
     * @param enabled New value of 启用.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Gets 启用.
     *
     * @return Value of 启用.
     */
    public boolean isEnabled() {
        return enabled;
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
}
