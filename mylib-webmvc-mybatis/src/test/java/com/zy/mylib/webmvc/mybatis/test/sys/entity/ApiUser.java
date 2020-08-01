package com.zy.mylib.webmvc.mybatis.test.sys.entity;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhouyang
 * @since 2020-07-25
 */
public class ApiUser implements Serializable {


    private static final long serialVersionUID = 3237411302995330359L;
    private String id;

    private String code;

    private String name;

    private String secret;

    private String system;


    /**
     * Gets id.
     *
     * @return Value of id.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets new system.
     *
     * @param system New value of system.
     */
    public void setSystem(String system) {
        this.system = system;
    }

    /**
     * Gets name.
     *
     * @return Value of name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets new name.
     *
     * @param name New value of name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets new secret.
     *
     * @param secret New value of secret.
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * Gets system.
     *
     * @return Value of system.
     */
    public String getSystem() {
        return system;
    }

    /**
     * Gets code.
     *
     * @return Value of code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets new id.
     *
     * @param id New value of id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets new code.
     *
     * @param code New value of code.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets secret.
     *
     * @return Value of secret.
     */
    public String getSecret() {
        return secret;
    }
}
