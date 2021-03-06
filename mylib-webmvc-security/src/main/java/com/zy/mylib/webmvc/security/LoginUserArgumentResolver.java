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
package com.zy.mylib.webmvc.security;

import com.zy.mylib.security.LoginUser;
import com.zy.mylib.security.Passport;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author ASUS
 */
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    Passport<LoginUser> passport;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return passport.getUser();
    }

    /**
     * Sets new passport.
     *
     * @param passport New value of passport.
     */
    public void setPassport(Passport<LoginUser> passport) {
        this.passport = passport;
    }

    /**
     * Gets passport.
     *
     * @return Value of passport.
     */
    public Passport<LoginUser> getPassport() {
        return passport;
    }
}
