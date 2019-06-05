package com.zy.mylib.security;

/**
 * @author ASUS
 */
public class LocalPhoneOAuthUser extends OAuthUser {
    /**
     * 手机验证码
     */
    private String code;
    /**
     * 手机号
     */
    private String phone;

    public LocalPhoneOAuthUser() {
    }


    /**
     * Sets new 手机验证码.
     *
     * @param code New value of 手机验证码.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Sets new 手机号.
     *
     * @param phone New value of 手机号.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets 手机验证码.
     *
     * @return Value of 手机验证码.
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets 手机号.
     *
     * @return Value of 手机号.
     */
    public String getPhone() {
        return phone;
    }
}
