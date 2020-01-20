package com.zy.mylib.data.jpa;


import org.springframework.core.annotation.AliasFor;

import javax.persistence.Entity;
import java.lang.annotation.*;

/**
 * @author ASUS
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EntityDescription {
    /**
     * 实体描述
     *
     * @return
     */
    String value() default "";
}
