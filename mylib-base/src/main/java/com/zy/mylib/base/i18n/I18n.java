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
package com.zy.mylib.base.i18n;


import com.zy.mylib.base.exception.BusException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class I18n {
    @Autowired(required = false)
    private LocalMessage message;
    static final Pattern messageCodePattern = Pattern.compile("^.*(\\d+)$");

    protected String getMessage(String code, Object... args) {
        return message.getMessage(code, args);
    }

    public BusException throwBusException(String code, String messagePrefix) {
        return new BusException(getMessage(messagePrefix + code), code);
    }

    /**
     * 返回异常
     *
     * @param messageTemplate 消息模板
     * @param args            模板参数
     * @return
     */
    public BusException throwBusExcption(String messageTemplate, Object... args) {
        Matcher m = messageCodePattern.matcher(messageTemplate);
        m.find();
        String code = m.group(1);
        return new BusException(getMessage(messageTemplate, args), code);
    }

    /**
     * 返回异常
     *
     * @param messageTemplate 消息模板
     * @param args            模板参数
     * @return
     */
    public BusException newBusException(String messageTemplate, Object... args) {
        Matcher m = messageCodePattern.matcher(messageTemplate);
        m.find();
        String code = m.group(1);
        return new BusException(getMessage(messageTemplate, args), code);
    }

    /**
     * 返回异常
     *
     * @param messageTemplate 消息模板
     * @param args            模板参数
     * @return
     */
    public BusException newBusException(Integer httpStatus, String messageTemplate, Object... args) {
        Matcher m = messageCodePattern.matcher(messageTemplate);
        m.find();
        String code = m.group(1);
        return new BusException(getMessage(messageTemplate, args), code, httpStatus);
    }
}
