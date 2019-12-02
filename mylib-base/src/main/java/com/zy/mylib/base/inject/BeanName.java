package com.zy.mylib.base.inject;

import javax.inject.Qualifier;
import java.lang.annotation.*;

/**
 * @author ASUS
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
@Target(value = {ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE, ElementType.METHOD})
public @interface BeanName {
    String value() default "";
}