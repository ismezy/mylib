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
    /**
     * 要登录的系统编号
     */
    private String system;

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

    /**
     * Gets 要登录的系统编号.
     *
     * @return Value of 要登录的系统编号.
     */
    public String getSystem() {
        return system;
    }

    /**
     * Sets new 要登录的系统编号.
     *
     * @param system New value of 要登录的系统编号.
     */
    public void setSystem(String system) {
        this.system = system;
    }
}
