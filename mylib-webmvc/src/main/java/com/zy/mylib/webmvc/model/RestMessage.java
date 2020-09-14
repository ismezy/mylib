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
package com.zy.mylib.webmvc.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.zy.mylib.base.model.BaseModel;

/**
 * @author 扬
 * @date 2017/1/10
 */
public class RestMessage {
    /**
     * 消息
     */
    String message;
    /**
     * 代码
     */
    String code;

    public RestMessage(String code, String message) {
        this.message = message;
        this.code = code;
    }

    /**
     * Sets new 消息.
     *
     * @param message New value of 消息.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets 代码.
     *
     * @return Value of 代码.
     */
    @JsonView(BaseModel.BaseView.class)
    public String getCode() {
        return code;
    }

    /**
     * Gets 消息.
     *
     * @return Value of 消息.
     */
    @JsonView(BaseModel.BaseView.class)
    public String getMessage() {
        return message;
    }

    /**
     * Sets new 代码.
     *
     * @param code New value of 代码.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 通用成功消息
     */
    public static final RestMessage SUCCESS;
    public static final RestMessage UNKNOW_ERROR;

    static {
        SUCCESS = new RestMessage("0000", "成功");
        UNKNOW_ERROR = new RestMessage("9999", "未知错误");
    }
}
