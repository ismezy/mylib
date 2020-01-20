package com.zy.mylib.mvc.logger;

import java.lang.annotation.*;

/**
 * 历史记录日志
 *
 * @author 扬
 * @date 2017/5/15
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HistoryLogger {
    /**
     * 操作类型或功能名称
     *
     * @return
     */
    String operateType();

    /**
     * 历史记录实体，必须指向继承自HistoryEntity的实体，可指向多个
     *
     * @return
     */
    String[] historyEntities();

    /**
     * 操作用户
     *
     * @return
     */
    String user() default "";
}
