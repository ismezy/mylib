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
