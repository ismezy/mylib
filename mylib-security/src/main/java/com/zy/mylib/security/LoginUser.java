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

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * OAuth2 用户
 *
 * @author ASUS
 */
public class LoginUser implements Serializable {
    private static final long serialVersionUID = 1843206312339055362L;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户中文名
     */
    private String userName;
    /**
     * 用户类型
     */
    private String type;

    /**
     * 角色
     */
    private Set<String> roles;
    /**
     * 访问策略
     */
    private Set<String> policy;
    /**
     * 当前登录系统编号
     */
    private String system;

    /**
     * 手机号码
     */
    private String telephone;

    /**
     * 所属机构名称
     */
    private String orgName;

    /**
     * 所属机构ID
     */
    private String orgId;
    /**
     * 用户所属机构树路径
     */
    private String orgPath;

    /**
     * 登录IP
     */
    private String ip;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 其他信息
     */
    private Map<String, String> other;

    /**
     * Gets 用户中文名.
     *
     * @return Value of 用户中文名.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets new 用户所属机构树路径.
     *
     * @param orgPath New value of 用户所属机构树路径.
     */
    public void setOrgPath(String orgPath) {
        this.orgPath = orgPath;
    }

    /**
     * Sets new 所属机构名称.
     *
     * @param orgName New value of 所属机构名称.
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * Sets new 用户中文名.
     *
     * @param userName New value of 用户中文名.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets 用户类型.
     *
     * @return Value of 用户类型.
     */
    public String getType() {
        return type;
    }

    /**
     * Gets 手机号码.
     *
     * @return Value of 手机号码.
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Gets 角色.
     *
     * @return Value of 角色.
     */
    public Set<String> getRoles() {
        return roles;
    }

    /**
     * Sets new 手机号码.
     *
     * @param telephone New value of 手机号码.
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Sets new 所属机构ID.
     *
     * @param orgId New value of 所属机构ID.
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * Gets 所属机构ID.
     *
     * @return Value of 所属机构ID.
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * Gets 所属机构名称.
     *
     * @return Value of 所属机构名称.
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * Sets new 角色.
     *
     * @param roles New value of 角色.
     */
    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    /**
     * Sets new 用户类型.
     *
     * @param type New value of 用户类型.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets 用户所属机构树路径.
     *
     * @return Value of 用户所属机构树路径.
     */
    public String getOrgPath() {
        return orgPath;
    }

    /**
     * Sets new 用户ID.
     *
     * @param userId New value of 用户ID.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets 用户ID.
     *
     * @return Value of 用户ID.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Gets 登录IP.
     *
     * @return Value of 登录IP.
     */
    public String getIp() {
        return ip;
    }

    /**
     * Sets new 登录IP.
     *
     * @param ip New value of 登录IP.
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return getUserName() + ":" + getUserId();
    }

    /**
     * Gets 当前登录系统编号.
     *
     * @return Value of 当前登录系统编号.
     */
    public String getSystem() {
        return system;
    }

    /**
     * Sets new 当前登录系统编号.
     *
     * @param system New value of 当前登录系统编号.
     */
    public void setSystem(String system) {
        this.system = system;
    }

    /**
     * Sets new 访问策略.
     *
     * @param policy New value of 访问策略.
     */
    public void setPolicy(Set<String> policy) {
        this.policy = policy;
    }

    /**
     * Gets 访问策略.
     *
     * @return Value of 访问策略.
     */
    public Set<String> getPolicy() {
        return policy;
    }

    /**
     * Sets new 其他信息.
     *
     * @param other New value of 其他信息.
     */
    public void setOther(Map<String, String> other) {
        this.other = other;
    }

    /**
     * Gets 其他信息.
     *
     * @return Value of 其他信息.
     */
    public Map<String, String> getOther() {
        return other;
    }

    /**
     * Sets new 最后登录时间.
     *
     * @param lastLoginTime New value of 最后登录时间.
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * Gets 最后登录时间.
     *
     * @return Value of 最后登录时间.
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }
}
