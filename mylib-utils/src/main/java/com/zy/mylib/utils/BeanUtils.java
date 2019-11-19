package com.zy.mylib.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 扬
 * @date 2017/5/15
 */
public class BeanUtils {
  /**
   * 复制合并实体
   *
   * @param target
   * @param source
   * @param source
   */
  public static void extend(Object target, Object... source) {
    for (Object s : source) {
      org.springframework.beans.BeanUtils.copyProperties(source, target);
    }
  }

  /**
   * 复制合并实体
   */
  public static void extend(Object target, Object source, Class classes) {
    org.springframework.beans.BeanUtils.copyProperties(source, target, classes);
  }

  static public Map<String, Object> getJoinPointParams(JoinPoint jp) {
    Map<String, Object> params = new HashMap<>();
    MethodSignature ms = (MethodSignature) jp.getSignature();
    String[] paramNames = ms.getParameterNames();
    for (int i = 0; i < paramNames.length; i++) {
      params.put(paramNames[i], jp.getArgs()[i]);
    }
    return params;
  }

  /**
   * 获取AOP方法注解
   *
   * @param jp   AOP切面参数, @See {@link JoinPoint}
   * @param type 注解类型
   * @param <T>  继承于Annotation @See {@link Annotation}
   * @return
   * @throws NoSuchMethodException
   * @throws SecurityException
   */
  static public <T extends Annotation> T getJoinPointAnnotation(JoinPoint jp, Class<T> type) throws NoSuchMethodException, SecurityException {
    MethodSignature ms = (MethodSignature) jp.getSignature();
    Class<?> classes = jp.getSignature().getDeclaringType();
    Class<?>[] argsType = ms.getParameterTypes();
    Method method = classes.getMethod(jp.getSignature().getName(), argsType);
    T logger = method.getAnnotation(type);
    return logger;
  }

  /**
   * 从AOP方法中获取HistroyEntity对象
   *
   * @param path 参数及属性,请参考
   * @param jp   AOP参数,请参考{@link JoinPoint}
   * @return
   * @throws NoSuchMethodException
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   */
  public static <T> T getEntityByMethodParamPath(String path, JoinPoint jp) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    MethodSignature ms = (MethodSignature) jp.getSignature();
    int firstDotIndex = path.indexOf('.');
    String paramName = path;
    String property = null;
    if (firstDotIndex >= 0) {
      paramName = path.substring(0, firstDotIndex);
      property = path.substring(firstDotIndex + 1);
    }
    int index = ArrayUtils.indexOf(ms.getParameterNames(), paramName);
    Object o = jp.getArgs()[index];
    T he = null;
    if (property == null) {
      he = (T) o;
    } else {
      he = (T) com.zy.mylib.utils.BeanUtils.getProperty(o, property);
    }
    return he;
  }

  /**
   * 获取对象属性
   *
   * @param o        对象
   * @param property 属性
   * @return
   */
  static public Object getProperty(Object o, String property) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    String[] properties = property.split("\\.");
    Method m = o.getClass().getMethod("get" + StringUtils.firstUpperCase(properties[0]));
    Object val = m.invoke(o);
    if (val != null && properties.length > 1) {
      return getProperty(val, StringUtils.join(properties, ".", 1));
    }
    return val;
  }
}
