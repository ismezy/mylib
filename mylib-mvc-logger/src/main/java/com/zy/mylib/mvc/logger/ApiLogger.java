package com.zy.mylib.mvc.logger;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * api日志记录注解
 * @author 扬
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)  
@Documented
public @interface ApiLogger {
	String type() default "";

	boolean console() default true;
	/**
	 * 是否记录数据库,默认true
	 * @return
	 */
	boolean db() default true;

	/**
	 * 请求文本模板，例：
	 *  @ApiLogger(request = "${loginUser?.userName}新增了字典[${codemap.code}]")
	 *  public String addCodemap(@RequestBody Codemap codemap);
	 * @return
	 */
	String request() default "";

	/**
	 * 成功时的文本模板默认值为"|,成功[${returnValue}]。"，例：
	 * @ApiLogger(request = "${loginUser?.userName}新增了字典[${codemap.code}]", success=",成功，id=${returnValue.id}")
	 * public String addCodemap(@RequestBody Codemap codemap);
	 * @return
	 */
	String success() default ",成功[${returnValue}]。";
	/**
	 * api失败时的文本模板默认值为",失败[${exception.message}]！"，例：
	 * @ApiLogger(request = "${loginUser?.userName}新增了字典[${codemap.code}]", error=",失败，原因为${exception.message}")
	 * public String addCodemap(@RequestBody Codemap codemap);
	 * @return
	 */
	String error() default  ",失败[${exception.message}]！";

	/**
	 * 目标id
	 * @return
	 */
	String id() default "";
}
