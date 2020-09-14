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
package com.zy.mylib.webmvc.base;

import com.zy.mylib.base.exception.BusException;
import com.zy.mylib.base.i18n.I18n;
import com.zy.mylib.base.i18n.LocalMessage;
import com.zy.mylib.webmvc.model.RestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author ASUS
 */
public class BaseRest extends I18n {
    protected Logger logger = LoggerFactory.getLogger(BaseRest.class);

    @Autowired(required = false)
    private LocalMessage message;

    @Value("${mylib.exception.defaultMessage:请求失败}")
    private String defaultMessage;


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RestMessage handleUncaughtException(Exception ex, WebRequest request, HttpServletResponse response) throws Exception {
        RestMessage result = new RestMessage("501", ex.getMessage());
        response.setStatus(500);
        if (ex instanceof BusException) {
            logger.warn(ex.getMessage());
            BusException bex = (BusException) ex;
            response.setStatus(bex.getHttpStatus());
            result.setCode(bex.getCode());
        } else if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException vex = (MethodArgumentNotValidException) ex;
            List<ObjectError> errors = vex.getBindingResult().getAllErrors();
            String message = "";
            for (ObjectError err : errors) {
                message += err.getDefaultMessage() + "！";
            }
            logger.warn(message);
            result.setMessage(message);
        } else if (ex instanceof BindException) {
            BindException bex = (BindException) ex;
            List<ObjectError> errors = bex.getBindingResult().getAllErrors();
            String message = "验证错误，共有" + errors.size() + "个错误：<br/>\n";
            for (ObjectError err : errors) {
                message += err.getDefaultMessage() + "<br/>\n";
            }
            logger.warn(message);
            result.setMessage(message);
        } else {
            logger.error(ex.getMessage(), ex);
            result.setMessage(defaultMessage);
        }
        return result;
    }
}
