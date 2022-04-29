/*
 * Copyright © 2020 ismezy (ismezy@qq.com)
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
package com.zy.mylib.mvc.logger

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.zy.mylib.base.i18n.I18n
import com.zy.mylib.security.LoginUser
import javax.inject.Inject
import com.zy.mylib.security.Passport
import kotlin.Throws
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.JoinPoint
import com.zy.mylib.data.jpa.HistoryEntity
import java.lang.NoSuchMethodException
import java.lang.IllegalAccessException
import java.lang.reflect.InvocationTargetException
import com.zy.mylib.base.model.BaseModel.DetailView
import com.zy.mylib.utils.BeanUtils
import com.zy.mylib.utils.StringUtils
import org.aspectj.lang.annotation.*
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.expression.spel.SpelParserConfiguration
import org.springframework.expression.spel.SpelCompilerMode
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.common.TemplateParserContext
import org.springframework.context.expression.MapAccessor
import org.springframework.expression.ParserContext
import org.springframework.expression.spel.support.StandardEvaluationContext
import java.lang.Exception
import java.lang.SecurityException
import java.util.*

/**
 * 日志记录类
 *
 * @author 扬
 */
@Aspect
class LoggerAspectAdvice<UT : LoginUser?> : I18n() {
  @Inject
  private val loggerService: LoggerService<UT>? = null

  @Inject
  private val operateHistory: OperateHistoryService<UT>? = null

  @Inject
  private val objectMapper: ObjectMapper? = null

  @Inject
  private val passport: Passport<UT>? = null
  @Pointcut("@annotation(com.zy.mylib.mvc.logger.ApiLogger)")
  fun serviceAspect() {
  }

  @Pointcut("@annotation(com.zy.mylib.mvc.logger.HistoryLogger)")
  fun historyAspect() {
  }

  @Around("historyAspect()")
  @Throws(Throwable::class)
  fun historyAspectAround(jp: ProceedingJoinPoint): Any {
    return try {
      val history = BeanUtils.getJoinPointAnnotation(jp, HistoryLogger::class.java)
      var user: UT? = null
      try {
        if (!StringUtils.isBlank(history.user)) {
          user = BeanUtils.getEntityByMethodParamPath(history.user, jp)
        } else if (passport!!.isAuthenticated) {
          user = passport.user
        }
        if (operateHistory != null) {
          for (entityName in history.historyEntities) {
            addHistory(history, entityName, user, jp, 1)
          }
        }
      } catch (e: Exception) {
      }
      val o = jp.proceed()
      if (operateHistory != null) {
        for (entityName in history.historyEntities) {
          addHistory(history, entityName, user, jp, 2)
        }
      }
      o
    } catch (ex: Throwable) {
      throw ex
    }
  }

  /**
   * 添加历史记录
   *
   * @param history    AOP注解
   * @param entityName 实体对象名称 ,请参考[HistoryLogger.historyEntities]
   * @param user       登录用户
   * @param jp         AOP参数,请参考[JoinPoint]
   * @param step       1 - 设置用户和时间字段 2 - 保存日志
   */
  private fun addHistory(history: HistoryLogger, entityName: String, user: UT?, jp: JoinPoint, step: Int) {
    try {
      val `object` = BeanUtils.getEntityByMethodParamPath<Any>(entityName, jp)
      if (`object` is Collection<*>) {
        for (o in `object`) {
          if (o is HistoryEntity) {
            addHistory(history, o, user, jp, step)
          }
        }
      } else if (`object` is HistoryEntity) {
        addHistory(history, `object`, user, jp, step)
      } else {
        operateHistory!!.addHistory("", history.operateType, user, `object`)
      }
    } catch (e: Exception) {
      logger.error("添加历史记录出错", e)
    }
  }

  /**
   * 添加历史记录
   *
   * @param history AOP注解
   * @param he      实体对象
   * @param user    登录用户
   * @param jp      AOP参数,请参考[JoinPoint]
   * @param step    1 - 设置用户和时间字段 2 - 保存日志
   */
  @Throws(NoSuchMethodException::class, IllegalAccessException::class, InvocationTargetException::class)
  private fun addHistory(history: HistoryLogger, he: HistoryEntity, user: UT?, jp: JoinPoint, step: Int) {
    if (user != null && step == 1) {
      val id = getIsCreate(he)
      if (id == null) {
        he.createTime = Date()
        he.createUserId = user.userId
      } else {
        he.lastModifyTime = Date()
        he.lastModifyUserId = user.userId
      }
    } else {
      if (step == 2) {
        val id = getIsCreate(he)
        try {
          val json = objectMapper!!.writerWithView(DetailView::class.java).writeValueAsString(he)
          operateHistory!!.addHistory(json, history.operateType, user, id)
        } catch (e: JsonProcessingException) {
          e.printStackTrace()
        }
      }
    }
  }

  /**
   * 根据实体对象ID属性是否为空判断是否创建新对象
   *
   * @param he
   * @return
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   */
  @Throws(NoSuchMethodException::class, InvocationTargetException::class, IllegalAccessException::class)
  private fun getIsCreate(he: Any): Any {
    val m = he.javaClass.getMethod("getId")
    return m.invoke(he)
  }

  /**
   * 获取执行成功日志正文
   *
   * @param jp
   * @param logger
   * @param retVal
   * @return
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   * @throws NoSuchMethodException
   */
  private fun getSuccessLoggerContent(jp: JoinPoint, logger: ApiLogger, retVal: Any): String {
    // 新方式
    val params = getAllParams(jp)
    params["returnValue"] = retVal
    return parseEL(logger.request + logger.success, params)
  }

  private fun getId(jp: JoinPoint, logger: ApiLogger, retVal: Any?): String {
    // 新方式
    val params = getAllParams(jp)
    params["returnValue"] = retVal
    return parseEL(logger.id, params)
  }

  private val config = SpelParserConfiguration(SpelCompilerMode.MIXED,
      this.javaClass.classLoader)
  private val parser = SpelExpressionParser(config)
  private val parserContext: ParserContext = TemplateParserContext("\${", "}")
  private val mapAccessor = MapAccessor()

  /**
   * 获取解析的EL表达式
   *
   * @param success
   * @param params
   * @return
   */
  private fun parseEL(success: String, params: Map<String, Any?>): String {
    return try {
      val context = StandardEvaluationContext(params)
      context.addPropertyAccessor(mapAccessor)
      val expr = parser.parseExpression(success, parserContext)
      expr.getValue(context, String::class.java)
    } catch (e: Exception) {
      logger.error("日志表达解析错误", e)
      ""
    }
  }

  /**
   * 获取日志描述定义
   *
   * @param jp
   * @return
   * @throws NoSuchMethodException
   * @throws SecurityException
   */
  @Throws(NoSuchMethodException::class, SecurityException::class)
  private fun <T : Annotation?> getLoggerDefine(jp: JoinPoint, type: Class<T>): T {
    return BeanUtils.getJoinPointAnnotation(jp, type)
  }

  @AfterReturning(pointcut = "serviceAspect()", returning = "retVal")
  fun afterServiceAspect(jp: JoinPoint, retVal: Any) {
    try {
      val loggerDef = getLoggerDefine(jp, ApiLogger::class.java)
      val loggerContent = getSuccessLoggerContent(jp, loggerDef, retVal)
      val id = getId(jp, loggerDef, retVal)
      writeLog(loggerDef, loggerContent, id)
    } catch (e: NoSuchMethodException) {
      e.printStackTrace()
    } catch (e: SecurityException) {
      e.printStackTrace()
    }
  }

  private fun writeLog(loggerDef: ApiLogger, loggerContent: String, id: String) {
    if (loggerDef.console) {
      if (passport!!.isAuthenticated) {
        val user = passport.user
        logger.info("{}, {} 用户:{},IP:{}", loggerDef.type, loggerContent, user, user!!.ip)
      } else {
        logger.info("{}, {}", loggerDef.type, loggerContent)
      }
    }
    if (loggerDef.db && loggerService != null) {
      var ip: String? = null;
      var user: UT? = null
      if (passport!!.isAuthenticated) {
        user = passport.user
        ip = user!!.ip
      }
      loggerService.addLog("", id, loggerDef.type, loggerContent, user, ip, Date())
    }
  }

  @AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
  fun afterServiceAspectThrowable(jp: JoinPoint, e: Throwable) {
    val loggerDef: ApiLogger
    try {
      loggerDef = getLoggerDefine(jp, ApiLogger::class.java)
      val loggerContent = getExceptionLoggerContent(jp, loggerDef, e)
      val id = getId(jp, loggerDef, null)
      writeLog(loggerDef, loggerContent, id)
    } catch (e1: NoSuchMethodException) {
      e1.printStackTrace()
    } catch (e1: SecurityException) {
      e1.printStackTrace()
    }
  }

  private fun getExceptionLoggerContent(jp: JoinPoint,
                                        logger: ApiLogger, e: Throwable): String {
    // 新方式
    val params = getAllParams(jp)
    params["exception"] = e
    return parseEL(logger.request + logger.error, params)
  }

  /**
   * 获取所有api方法的参数和登录用户信息
   *
   * @param jp
   * @return
   */
  private fun getAllParams(jp: JoinPoint): MutableMap<String, Any?> {
    val result: MutableMap<String, Any?> = HashMap(5)
    val ms = jp.signature as MethodSignature
    val params = ms.parameterNames
    val args = jp.args
    for (i in params.indices) {
      result[params[i]] = args[i]
    }
    return result
  }

  companion object {
    val logger = LoggerFactory.getLogger(LoggerAspectAdvice::class.java)
  }
}
