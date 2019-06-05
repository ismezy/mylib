package com.zy.mylib.security;

import java.io.Serializable;

/**
 * oauth 用户验证器
 * @author ASUS
 */
public interface OAuthUserVerifier<IN extends OAuthUser ,OUT extends Serializable> {
    /**
     * 如果可校验通过返回用户，否则抛出BusException
     * @param oauthUser
     * @return
     */
    OUT verify(IN oauthUser);
}
