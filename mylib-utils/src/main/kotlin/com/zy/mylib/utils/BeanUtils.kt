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
package com.zy.mylib.utils

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.BeanUtils
import java.lang.reflect.InvocationTargetException

/**
 * @author 扬
 * @date 2017/5/15
 */
object BeanUtils {
  /**
   * 复制合并实体
   *
   * @param target
   * @param source
   * @param source
   */
  fun extend(target: Any?, vararg source: Any?) {
    for (s in source) {
      BeanUtils.copyProperties(source, target)
    }
  }

  /**
   * 复制合并实体
   */
  fun extend(target: Any?, source: Any?, classes: Class<*>?) {
    BeanUtils.copyProperties(source, target, classes)
  }

  fun getJoinPointParams(jp: JoinPoint): Map<String, Any> {
    val params: MutableMap<String, Any> = HashMap()
    val ms = jp.signature as MethodSignature
    val paramNames = ms.parameterNames
    for (i in paramNames.indices) {
      params[paramNames[i]] = jp.args[i]
    }
    return params
  }

  /**
   * 获取AOP方法注解
   *
   * @param jp   AOP切面参数, @See [JoinPoint]
   * @param type 注解类型
   * @param <T>  继承于Annotation @See [Annotation]
   * @return
   * @throws NoSuchMethodException
   * @throws SecurityException
  </T> */
  @Throws(NoSuchMethodException::class, SecurityException::class)
  fun <T : Annotation?> getJoinPointAnnotation(jp: JoinPoint, type: Class<T>?): T {
    val ms = jp.signature as MethodSignature
    val classes = jp.signature.declaringType
    val argsType = ms.parameterTypes
    val method = classes.getMethod(jp.signature.name, *argsType)
    return method.getAnnotation(type)
  }

  /**
   * 从AOP方法中获取HistroyEntity对象
   *
   * @param path 参数及属性,请参考
   * @param jp   AOP参数,请参考[JoinPoint]
   * @return
   * @throws NoSuchMethodException
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   */
  @Throws(NoSuchMethodException::class, IllegalAccessException::class, InvocationTargetException::class)
  fun <T> getEntityByMethodParamPath(path: String, jp: JoinPoint): T? {
    val ms = jp.signature as MethodSignature
    val firstDotIndex = path.indexOf('.')
    var paramName = path
    var property: String? = null
    if (firstDotIndex >= 0) {
      paramName = path.substring(0, firstDotIndex)
      property = path.substring(firstDotIndex + 1)
    }
    val index = ArrayUtils.indexOf(ms.parameterNames, paramName)
    val o = jp.args[index]
    var he: T? = null
    he = if (property == null) {
      o as T
    } else {
      getProperty(o, property) as T?
    }
    return he
  }

  /**
   * 获取对象属性
   *
   * @param o        对象
   * @param property 属性
   * @return
   */
  @Throws(NoSuchMethodException::class, InvocationTargetException::class, IllegalAccessException::class)
  fun getProperty(o: Any, property: String?): Any? {
    val properties = property!!.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    val m = o.javaClass.getMethod("get" + StringUtils.firstUpperCase(properties[0]))
    val `val` = m.invoke(o)
    return if (`val` != null && properties.size > 1) {
      getProperty(`val`, StringUtils.join(properties, ".", 1))
    } else `val`
  }
}
