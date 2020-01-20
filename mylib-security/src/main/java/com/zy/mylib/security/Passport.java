package com.zy.mylib.security;

import java.io.Serializable;

public interface Passport<T extends Serializable> {
    T getUser();

    boolean isAuthenticated();

    /**
     * 登录，返回token
     *
     * @param user
     * @return
     */
    String login(T user);

    void logout();

    /**
     * passport 类型
     *
     * @return
     */
    String getPrivoder();
}
