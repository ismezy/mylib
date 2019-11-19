package com.zy.mylib.mvc.logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zy.mylib.base.i18n.I18n;
import com.zy.mylib.base.model.BaseModel;
import com.zy.mylib.data.jpa.HistoryEntity;
import com.zy.mylib.security.LoginUser;
import com.zy.mylib.security.Passport;
import com.zy.mylib.utils.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志记录类
 *
 * @author 扬
 */
@Aspect
public class LoggerAspectAdvice<UT extends LoginUser> extends I18n {
  static final Logger logger = LoggerFactory.getLogger(LoggerAspectAdvice.class);

  @Inject
  private LoggerService<UT> loggerService;
  @Inject
  private OperateHistoryService<UT> operateHistory;
  @Inject
  private ObjectMapper objectMapper;
  @Inject
  private Passport<UT> passport;

  @Pointcut("@annotation(com.zy.mylib.mvc.logger.ApiLogger)")
  public void serviceAspect() {

  }

  @Pointcut("@annotation(com.zy.mylib.mvc.logger.HistoryLogger)")
  public void historyAspect() {

  }

  @Around("historyAspect()")
  public Object historyAspectAround(ProceedingJoinPoint jp) throws Throwable {
    try {
      HistoryLogger history = com.zy.mylib.utils.BeanUtils.getJoinPointAnnotation(jp, HistoryLogger.class);
      UT user = null;
      try {
        if (!StringUtils.isBlank(history.user())) {
          user = com.zy.mylib.utils.BeanUtils.getEntityByMethodParamPath(history.user(), jp);
        } else if (passport.isAuthenticated()) {
          user = passport.getUser();
        }
        if (this.operateHistory != null) {
          for (String entityName : history.historyEntities()) {
            addHistory(history, entityName, user, jp, 1);
          }
        }
      } catch (Exception e) {
      }
      Object o = jp.proceed();
      if (this.operateHistory != null) {
        for (String entityName : history.historyEntities()) {
          addHistory(history, entityName, user, jp, 2);
        }
      }
      return o;
    } catch (Throwable ex) {
      throw ex;
    }
  }

  /**
   * 添加历史记录
   *
   * @param history    AOP注解
   * @param entityName 实体对象名称 ,请参考{@link HistoryLogger#historyEntities}
   * @param user       登录用户
   * @param jp         AOP参数,请参考{@link JoinPoint}
   * @param step       1 - 设置用户和时间字段 2 - 保存日志
   */
  private void addHistory(HistoryLogger history, String entityName, UT user, JoinPoint jp, int step) {
    try {
      Object object = com.zy.mylib.utils.BeanUtils.getEntityByMethodParamPath(entityName, jp);
      if (object instanceof Collection) {
        Collection collection = (Collection) object;
        for (Object o : collection) {
          if (o instanceof HistoryEntity) {
            addHistory(history, (HistoryEntity) o, user, jp, step);
          }
        }
      } else if (object instanceof HistoryEntity) {
        addHistory(history, (HistoryEntity) object, user, jp, step);
      } else {
        operateHistory.addHistory("", history.operateType(), user, object);
      }
    } catch (Exception e) {
      logger.error("添加历史记录出错", e);
    }
  }

  /**
   * 添加历史记录
   *
   * @param history AOP注解
   * @param he      实体对象
   * @param user    登录用户
   * @param jp      AOP参数,请参考{@link JoinPoint}
   * @param step    1 - 设置用户和时间字段 2 - 保存日志
   */
  private void addHistory(HistoryLogger history, HistoryEntity he, UT user, JoinPoint jp, int step) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    if (user != null && step == 1) {
      Object id = getIsCreate(he);
      if (id == null) {
        he.setCreateTime(new Date());
        he.setCreateUserId(user.getUserId());
      } else {
        he.setLastModifyTime(new Date());
        he.setLastModifyUserId(user.getUserId());
      }
    } else {
      if (step == 2) {
        Object id = getIsCreate(he);
        try {
          String json = objectMapper.writerWithView(BaseModel.DetailView.class).writeValueAsString(he);
          operateHistory.addHistory(json, history.operateType(), user, id);
        } catch (JsonProcessingException e) {
          e.printStackTrace();
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
  private Object getIsCreate(Object he) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method m = he.getClass().getMethod("getId");
    Object o = m.invoke(he);
    return o;
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
  private String getSuccessLoggerContent(JoinPoint jp, ApiLogger logger, Object retVal) {
    // 新方式
    Map<String, Object> params = this.getAllParams(jp);
    params.put("returnValue", retVal);
    return parseEL(logger.request() + logger.success(), params);
  }

  private String getId(JoinPoint jp, ApiLogger logger, Object retVal) {
    // 新方式
    Map<String, Object> params = this.getAllParams(jp);
    params.put("returnValue", retVal);
    return parseEL(logger.id(), params);
  }

  private SpelParserConfiguration config = new SpelParserConfiguration(SpelCompilerMode.MIXED,
    this.getClass().getClassLoader());
  private SpelExpressionParser parser = new SpelExpressionParser(config);
  private ParserContext parserContext = new TemplateParserContext("${", "}");
  private MapAccessor mapAccessor = new MapAccessor();

  /**
   * 获取解析的EL表达式
   *
   * @param success
   * @param params
   * @return
   */
  private String parseEL(String success, Map<String, Object> params) {
    try {
      StandardEvaluationContext context = new StandardEvaluationContext(params);
      context.addPropertyAccessor(mapAccessor);
      Expression expr = parser.parseExpression(success, parserContext);
      String ret = expr.getValue(context, String.class);
      return ret;
    } catch (Exception e) {
      logger.error("日志表达解析错误", e);
      return "";
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
  private <T extends Annotation> T getLoggerDefine(JoinPoint jp, Class<T> type) throws NoSuchMethodException, SecurityException {
    return com.zy.mylib.utils.BeanUtils.getJoinPointAnnotation(jp, type);
  }

  @AfterReturning(pointcut = "serviceAspect()", returning = "retVal")
  public void afterServiceAspect(JoinPoint jp, Object retVal) {
    try {
      ApiLogger loggerDef = getLoggerDefine(jp, ApiLogger.class);
      String loggerContent = getSuccessLoggerContent(jp, loggerDef, retVal);
      String id = getId(jp, loggerDef, retVal);
      writeLog(loggerDef, loggerContent, id);
    } catch (NoSuchMethodException | SecurityException e) {
      e.printStackTrace();
    }
  }

  private void writeLog(ApiLogger loggerDef, String loggerContent, String id) {
    if (loggerDef.console()) {
      if (passport.isAuthenticated()) {
        UT user = passport.getUser();
        logger.info("{}, {} 用户:{},IP:{}", loggerDef.type(), loggerContent, user, user.getIp());
      } else {
        logger.info("{}, {}", loggerDef.type(), loggerContent);
      }
    }
    if (loggerDef.db() && loggerService != null) {
      String ip = "";
      UT user = null;
      if (passport.isAuthenticated()) {
        user = passport.getUser();
        ip = user.getIp();
      }
      loggerService.addLog("", id, loggerDef.type(), loggerContent, user, ip, new Date());
    }
  }

  @AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
  public void afterServiceAspectThrowable(JoinPoint jp, Throwable e) {
    ApiLogger loggerDef;
    try {
      loggerDef = getLoggerDefine(jp, ApiLogger.class);
      String loggerContent = getExceptionLoggerContent(jp, loggerDef, e);
      String id = getId(jp, loggerDef, null);
      writeLog(loggerDef, loggerContent, id);
    } catch (NoSuchMethodException | SecurityException e1) {
      e1.printStackTrace();
    }
  }


  private String getExceptionLoggerContent(JoinPoint jp,
                                           ApiLogger logger, Throwable e) {
    // 新方式
    Map<String, Object> params = this.getAllParams(jp);
    params.put("exception", e);
    return parseEL(logger.request() + logger.error(), params);
  }

  /**
   * 获取所有api方法的参数和登录用户信息
   *
   * @param jp
   * @return
   */
  private Map<String, Object> getAllParams(JoinPoint jp) {
    Map<String, Object> result = new HashMap<>(5);
    MethodSignature ms = (MethodSignature) jp.getSignature();
    String[] params = ms.getParameterNames();
    Object[] args = jp.getArgs();
    for (int i = 0; i < params.length; i++) {
      result.put(params[i], args[i]);
    }
    return result;
  }
}
