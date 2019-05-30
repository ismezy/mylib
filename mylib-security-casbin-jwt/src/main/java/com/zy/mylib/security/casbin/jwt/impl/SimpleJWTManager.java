package com.zy.mylib.security.casbin.jwt.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zy.mylib.security.casbin.jwt.JWTManager;

/**
 * jwt manager最简实现
 * @author ASUS
 */
public class SimpleJWTManager implements JWTManager {
    /**
     * jwt 加密算法
     */
    private Algorithm algorithm;
    @Override
    public DecodedJWT decode(String tokenString) {
        return JWT.require(algorithm).build().verify(tokenString);
    }

    @Override
    public String sign(String user, String phone, String name, String role) {
        return JWT.create().withSubject(user)
                .withClaim("name", name)
                .withClaim("phone", phone)
                .withClaim("role", role)
                .sign(algorithm);
    }

    /**
     * Gets jwt 加密算法.
     *
     * @return Value of jwt 加密算法.
     */
    public Algorithm getAlgorithm() {
        return algorithm;
    }

    /**
     * Sets new jwt 加密算法.
     *
     * @param algorithm New value of jwt 加密算法.
     */
    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }
}
