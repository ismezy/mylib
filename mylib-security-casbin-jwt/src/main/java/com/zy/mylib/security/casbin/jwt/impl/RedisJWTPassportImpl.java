package com.zy.mylib.security.casbin.jwt.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zy.mylib.base.exception.BusException;
import com.zy.mylib.cache.redis.RedisUserCache;
import com.zy.mylib.security.LoginUser;
import com.zy.mylib.security.Passport;
import com.zy.mylib.utils.StringUtils;
import org.springframework.beans.factory.InitializingBean;
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
@Named
public class RedisJWTPassportImpl implements Passport<LoginUser>, InitializingBean {
    final static String HEADER_TOKEN_KEY = "token";
    private RedisUserCache cache;
    @Inject @Named("jwtTokenRedisOperations")
    private RedisOperations<String, ? extends Serializable> redisOperations;

    private Algorithm algorithm = Algorithm.HMAC256("dduptop.com");
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
    public LoginUser getUser() {
        String token = this.getRequest().getHeader(HEADER_TOKEN_KEY);
        return cache.get(token, "user");
    }

    @Override
    public boolean isAuthenticated() {
        String token = this.getRequest().getHeader(HEADER_TOKEN_KEY);
        if(StringUtils.isBlank(token)) {
            return false;
        }
        Boolean b = cache.get(token, "isAuthenticated");
        return b == null ? false : b;
    }

    @Override
    public String login(LoginUser user) {
        Date now = new Date();
        String token = JWT.create().withSubject(user.getUserId())
                .withClaim("phone", user.getTelephone())
                .withClaim("name", user.getUserName())
                .withClaim("type", user.getType())
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
        cache.put(token, "user", user);
        cache.put(token, "isAuthenticated", true);
        return token;
    }

    @Override
    public void logout() {
        String token = this.getRequest().getHeader(HEADER_TOKEN_KEY);
        cache.removeToken(token);
    }

    @Override
    public String getPrivoder() {
        return "local";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        cache = new RedisUserCache(redisOperations, 1, TimeUnit.DAYS);
    }


}
