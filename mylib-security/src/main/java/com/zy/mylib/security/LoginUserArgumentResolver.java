package com.zy.mylib.security;

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
