package com.zy.mylib.security;

/**
 * oauth user
 *
 * @author ASUS
 */
public class OAuthUser {
    /**
     * 用户服务提供方唯一用户id
     */
    private String id;
    /**
     * 用户提供方
     */
    private String provider;

    /**
     * Sets new 用户提供方.
     *
     * @param provider New value of 用户提供方.
     */
    public void setProvider(String provider) {
        this.provider = provider;
    }

    /**
     * Sets new 用户服务提供方唯一用户id.
     *
     * @param id New value of 用户服务提供方唯一用户id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets 用户提供方.
     *
     * @return Value of 用户提供方.
     */
    public String getProvider() {
        return provider;
    }

    /**
     * Gets 用户服务提供方唯一用户id.
     *
     * @return Value of 用户服务提供方唯一用户id.
     */
    public String getId() {
        return id;
    }
}
