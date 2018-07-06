package com.zy.mylib.base.i18n;


import com.zy.mylib.base.exception.BusException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class I18n {
	@Autowired(required=false)
	private LocalMessage message;
	static final Pattern messageCodePattern = Pattern.compile("^.*(\\d+)$");
	protected String getMessage(String code,Object...args) {
		return message.getMessage(code, args);
	}

	public BusException throwBusException(String code, String messagePrefix){
		return new BusException(getMessage(messagePrefix+code),code);
	}
	/**
	 * 返回异常
	 * @param messageTemplate 消息模板
	 * @param args 模板参数
	 * @return
	 */
	public BusException throwBusExcption(String messageTemplate,Object...args){
		Matcher m = messageCodePattern.matcher(messageTemplate);
		m.find();
		String code = m.group(1);
		return new BusException(getMessage(messageTemplate,args), code);
	}
	/**
	 * 返回异常
	 * @param messageTemplate 消息模板
	 * @param args 模板参数
	 * @return
	 */
	public BusException newBusException(String messageTemplate, Object...args){
		Matcher m = messageCodePattern.matcher(messageTemplate);
		m.find();
		String code = m.group(1);
		return new BusException(getMessage(messageTemplate,args), code);
	}
	/**
	 * 返回异常
	 * @param messageTemplate 消息模板
	 * @param args 模板参数
	 * @return
	 */
	public BusException newBusException(Integer httpStatus, String messageTemplate, Object...args){
		Matcher m = messageCodePattern.matcher(messageTemplate);
		m.find();
		String code = m.group(1);
		return new BusException(getMessage(messageTemplate,args), code, httpStatus);
	}
}
