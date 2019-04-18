package com.zy.mylib.mvc.logger;

import java.lang.annotation.*;

/**
 * 日志描述参数定义
 * @author 扬
 * @deprecated 现在使用@ApiLogger(request = "", success="", error="")
 *
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)  
@Documented
@Deprecated
public @interface ParamDefine {
	/**
	 * 属性，如果设置了此字段则从from的目标的属性上获取值，否则直接获取from
	 * @return
	 */
	String property() default "";
	/**
	 * 如果设置from为0，则用此字段指定参数index，如要获取第3个参数就指定index为2
	 * @return
	 */
	int paramIndex() default 0;
}
