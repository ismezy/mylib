package com.zy.mylib.security.casbin.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * jwt token管理
 */
public interface JWTManager {
    DecodedJWT decode(String tokenString);
    String sign(String user, String phone, String name, String role);
}
