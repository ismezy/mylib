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
	/**
	 * api访问日志，可用format格式
	 * @deprecated
	 * 现在使用@ApiLogger(request = "", success="", error="")
	 * @return
	 */
	@Deprecated
	String content() default "";
	/**
	 * api访问结果日志，可用format格式
	 * @deprecated 现在使用@ApiLogger(request = "", success="", error="")
	 * @return
	 */
	@Deprecated
	String ret() default "";

	/**
	 * 操作日志内容参数描述
	 * @return
	 */
	ParamDefine[] paramProperties() default {};
	@Deprecated
	// warning: "地地地地"
	String[] retProperties() default {};

	/**
	 * @deprecated 现在使用@ApiLogger(request = "", success="", error="")
	 * @return
	 */
	@Deprecated
	String cloudUser() default "";
	String type() default "";
	/**
	 * 是否记录到文件,默认true
	 * @deprecated 使用console = true
	 * @return
	 */
	@AliasFor("console")
	@Deprecated
	boolean file() default true;

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
	 * 成功时的文本模板默认值为"|,成功[${resultValue}]。"，例：
	 * @ApiLogger(request = "${loginUser?.userName}新增了字典[${codemap.code}]", success=",成功，id=${resultValue.id}")
	 * public String addCodemap(@RequestBody Codemap codemap);
	 * @return
	 */
	String success() default ",成功[${resultValue}]。";
	/**
	 * api失败时的文本模板默认值为",失败[${exception.message}]！"，例：
	 * @ApiLogger(request = "${loginUser?.userName}新增了字典[${codemap.code}]", error=",失败，原因为${exception.message}")
	 * public String addCodemap(@RequestBody Codemap codemap);
	 * @return
	 */
	String error() default  ",失败[${exception.message}]！";
}
