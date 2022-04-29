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

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zy.mylib.base.exception.BusException;
import com.zy.mylib.cache.redis.RedisUserCache;
import com.zy.mylib.security.LoginUser;
import com.zy.mylib.security.Passport;
import com.zy.mylib.utils.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author ASUS
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
@Named
public class RedisJWTPassportImpl implements Passport<LoginUser>, InitializingBean {
    final static String HEADER_TOKEN_KEY = "token";
    final static String QUERY_TOKEN_KEY = "__token";

    private RedisUserCache userCache;

    /**
     * 登录重试次数,默认5次
     */
    @Value("${mylib.auth.retry:5}")
    private Integer retry;
    /**
     * 拒绝登录时间，分钟(默认10分钟)
     */
    @Value("${mylib.auth.rejectMin:10}")
    private Integer rejectMin;

    @Inject
    @Named("jwtTokenRedisOperations")
    private RedisOperations<String, Serializable> redisOperations;

    private Algorithm algorithm = Algorithm.HMAC256(Passport.HMAC_SECRET);
//
//    public RedisJWTPassportImpl(RedisOperations<String, ? extends Serializable> redisOperations) {
//        this.redisOperations = redisOperations;
//    }

    public HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            throw BusException.builder().message("当前线程中不存在 Request 上下文").build();
        }
        return attrs.getRequest();
    }

    @Override
    public String getToken() {
        HttpServletRequest request = this.getRequest();
        String headerToken = request.getHeader(HEADER_TOKEN_KEY);
        if (headerToken != null) {
            return headerToken;
        } else {
            String queryToken = request.getParameter(QUERY_TOKEN_KEY);
            return queryToken;
        }
    }

    @Override
    public LoginUser getUser() {
        String token = getToken();
        return StringUtils.isBlank(token) ? null : userCache.get(token, "user");
    }

    @Override
    public boolean isAuthenticated() {
        String token = getToken();
        if (StringUtils.isBlank(token)) {
            return false;
        }
        Boolean b = userCache.get(token, "isAuthenticated");
        return b == null ? false : b;
    }

    @Override
    public String login(LoginUser user) {
        Date now = new Date();
        String token = JWT.create().withSubject(user.getUserId())
                .withClaim("phone", user.getTelephone())
                .withClaim("name", user.getUserName())
                .withClaim("type", user.getType())
                .withClaim("orgId", user.getOrgId())
                .withClaim("orgName", user.getOrgName())
                .withClaim("orgPath", user.getOrgPath())
                .withClaim("system", user.getSystem())
                .withClaim("loginUser", user.getUserId())
                .withArrayClaim("roles", user.getRoles().toArray(new String[0]))
                .withIssuedAt(now)
                // 1天有效
                .withExpiresAt(new Date(now.getTime() + 1000 * 60 * 60 * 24))
                .sign(algorithm);

        HttpServletRequest request = getRequest();
        String remoteAddr = request.getHeader("X-FORWARDED-FOR");
        if (remoteAddr == null || "".equals(remoteAddr)) {
            remoteAddr = request.getRemoteAddr();
        }
        user.setIp(remoteAddr);
        userCache.put(token, "user", user);
        userCache.put(token, "isAuthenticated", true);
        return token;
    }

    @Override
    public void onLoginFailed(String user) {
        HttpServletRequest request = getRequest();
        String remoteAddr = request.getHeader("X-FORWARDED-FOR");
        if (StringUtils.isBlank(remoteAddr)) {
            remoteAddr = request.getRemoteAddr();
        }
        String key = "login:failed:" + remoteAddr + ":" + user;
        Integer count = (Integer) redisOperations.opsForValue().get(key);

        if(count == null) {
            count = 1;
            redisOperations.opsForValue().set(key, count, rejectMin, TimeUnit.MINUTES);
        }  else {
            redisOperations.opsForValue().set(key, count + 1);
            redisOperations.expire(key, rejectMin, TimeUnit.MINUTES);
        }
    }

    @Override
    public boolean isLock(String user) {
        HttpServletRequest request = getRequest();
        String remoteAddr = request.getHeader("X-FORWARDED-FOR");
        if (StringUtils.isBlank(remoteAddr)) {
            remoteAddr = request.getRemoteAddr();
        }
        String key = "login:failed:" + remoteAddr + ":" + user;
        Integer count = (Integer) redisOperations.opsForValue().get(key);
        if(count != null && count >= this.retry) {
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(LoginUser user) {
        userCache.put(getToken(), "user", user);
    }

    @Override
    public void logout() {
        userCache.removeToken(getToken());
    }

    @Override
    public String getPrivoder() {
        return "local";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        userCache = new RedisUserCache(redisOperations, 1, TimeUnit.DAYS);
    }


    /**
     * Gets algorithm.
     *
     * @return Value of algorithm.
     */
    public Algorithm getAlgorithm() {
        return algorithm;
    }

    /**
     * Gets cache.
     *
     * @return Value of cache.
     */
    public RedisUserCache getUserCache() {
        return userCache;
    }

    /**
     * Sets new cache.
     *
     * @param userCache New value of cache.
     */
    public void setUserCache(RedisUserCache userCache) {
        this.userCache = userCache;
    }

    /**
     * Sets new algorithm.
     *
     * @param algorithm New value of algorithm.
     */
    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * Gets redisOperations.
     *
     * @return Value of redisOperations.
     */
    public RedisOperations<String, ? extends Serializable> getRedisOperations() {
        return redisOperations;
    }

    /**
     * Sets new redisOperations.
     *
     * @param redisOperations New value of redisOperations.
     */
    public void setRedisOperations(RedisOperations<String, Serializable> redisOperations) {
        this.redisOperations = redisOperations;
    }
}
