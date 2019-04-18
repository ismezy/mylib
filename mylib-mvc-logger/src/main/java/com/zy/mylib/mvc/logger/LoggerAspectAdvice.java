package com.zy.mylib.mvc.logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zy.mylib.base.i18n.I18n;
import com.zy.mylib.data.jpa.HistoryEntity;
import com.zy.mylib.data.jpa.UUIDBaseEntity;
import com.zy.mylib.security.utils.UserUtils;
import com.zy.mylib.utils.DateUtils;
import com.zy.mylib.utils.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志记录类
 * @author 扬
 *
 */
@Aspect
public class LoggerAspectAdvice<UT extends UUIDBaseEntity> extends I18n {
	static final Logger logger = LoggerFactory.getLogger(LoggerAspectAdvice.class);

	@Autowired(required = false)
	private LoggerService<UT> loggerService;
    @Autowired(required = false)
    private OperateHistoryService<UT> operateHistory;
    @Autowired
    private ObjectMapper objectMapper;

	@Pointcut("@annotation(com.zy.mylib.mvc.logger.ApiLogger)")
	public void serviceAspect(){
		
	}
	@Pointcut("@annotation(com.zy.mylib.mvc.logger.HistoryLogger)")
	public void historyAspect(){

    }

    @Around("historyAspect()")
    public Object historyAspectAround(ProceedingJoinPoint jp) throws Throwable {
        try {
            HistoryLogger history = com.zy.mylib.utils.BeanUtils.getJoinPointAnnotation(jp, HistoryLogger.class);
            UT user = null;
            try {
                if (!StringUtils.isBlank(history.user())) {
                    user = com.zy.mylib.utils.BeanUtils.getEntityByMethodParamPath(history.user(), jp);
                } else if (SecurityUtils.getSubject().isAuthenticated()) {
                    user = UserUtils.getCurrentUser();
                }
                if (this.operateHistory != null) {
                    for (String entityName : history.historyEntities()) {
                        addHistory(history, entityName, user, jp, 1);
                    }
                }
            }catch (Exception e){}
            Object o = jp.proceed();
            if(this.operateHistory != null) {
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
     * @param history AOP注解
     * @param entityName 实体对象名称 ,请参考{@link HistoryLogger#historyEntities}
     * @param user 登录用户
     * @param jp AOP参数,请参考{@link JoinPoint}
     * @param step 1 - 设置用户和时间字段 2 - 保存日志
     */
    private void addHistory(HistoryLogger history, String entityName, UT user, JoinPoint jp,int step) {
        try {
            Object object = com.zy.mylib.utils.BeanUtils.getEntityByMethodParamPath(entityName,jp);
            if(object instanceof Collection){
                Collection collection = (Collection) object;
                for (Object o: collection ) {
                    if(o instanceof HistoryEntity) {
						addHistory(history, (HistoryEntity) o, user, jp, step);
					}
                }
            } else if(object instanceof HistoryEntity){
                addHistory(history, (HistoryEntity) object, user, jp, step);
            }else {
				operateHistory.addHistory("", history.operateType(), user, object);
			}
        }
        catch (Exception e){
            logger.error("添加历史记录出错",e);
        }
    }
    /**
     * 添加历史记录
     * @param history AOP注解
     * @param he 实体对象
     * @param user 登录用户
     * @param jp AOP参数,请参考{@link JoinPoint}
     * @param step 1 - 设置用户和时间字段 2 - 保存日志
     */
    private void addHistory(HistoryLogger history, HistoryEntity he, UT user, JoinPoint jp, int step) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if(user != null && step == 1) {
            Object id = getIsCreate(he);
            if (id == null) {
                he.setCreateTime(new Date());
                he.setCreateUserId(user.getId());
            } else {
                he.setLastModifyTime(new Date());
                he.setLastModifyUserId(user.getId());
            }
        }else {
            if (step == 2) {
                Object id = getIsCreate(he);
                try {
                    String json = objectMapper.writeValueAsString(he);
                    operateHistory.addHistory(json, history.operateType(), user, id);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据实体对象ID属性是否为空判断是否创建新对象
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
	 * @param jp
	 * @param logger
	 * @param retVal 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private String getSuccessLoggerContent(JoinPoint jp, ApiLogger logger, Object retVal) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		// 新方式
		if(StringUtils.isNotBlank(logger.request())) {
			Map<String, Object> params  = this.getAllParams(jp);
			params.put("returnValue", retVal);
			return parseEL(logger.request() + logger.success(), params);
		}
		Object[] args = jp.getArgs();
		ParamDefine[] pds = logger.paramProperties();
		Object[] msgParam = new Object[pds.length];
		for (int i = 0; i < msgParam.length; i++) {
			Object tmp = args[pds[i].paramIndex()];
			if(StringUtils.isBlank(pds[i].property())){
				msgParam[i] = tmp;
			}
			else{
				msgParam[i] = BeanUtils.getProperty(tmp, pds[i].property());
			}
		}
		// 执行成功描述，可为空
		String desc = String.format(logger.content(), msgParam);
		if(StringUtils.isNotBlank(logger.ret())){
			Object[] retParam = new Object[logger.retProperties().length];
			for (int i = 0; i < retParam.length; i++) {
				if(StringUtils.isBlank(logger.retProperties()[i])){
					retParam[i] = retVal;
				}
				else{
					retParam[i] = BeanUtils.getProperty(retVal, logger.retProperties()[i]);
				}
			}
			desc += String.format(logger.ret(), retParam);
		}
		return desc;
	}
	private SpelParserConfiguration config = new SpelParserConfiguration(SpelCompilerMode.MIXED,
			this.getClass().getClassLoader());
	private SpelExpressionParser parser = new SpelExpressionParser(config);
	private ParserContext parserContext = new TemplateParserContext("${", "}");
	private MapAccessor mapAccessor = new MapAccessor();

	/**
	 * 获取解析的EL表达式
	 * @param success
	 * @param params
	 * @return
	 */
	private String parseEL(String success, Map<String, Object> params) {
		StandardEvaluationContext context = new StandardEvaluationContext(params);
		context.addPropertyAccessor(mapAccessor);
		Expression expr = parser.parseExpression(success, parserContext);
		String ret = expr.getValue(context, String.class);
		return ret;
	}

	/**
	 * 获取日志描述定义
	 * @param jp
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	private <T extends Annotation> T getLoggerDefine(JoinPoint jp, Class<T> type) throws NoSuchMethodException, SecurityException {
		return com.zy.mylib.utils.BeanUtils.getJoinPointAnnotation(jp,type);
	}

	@AfterReturning(pointcut="serviceAspect()", returning="retVal")
	public void afterServiceAspect(JoinPoint jp, Object retVal){
		try {
			ApiLogger loggerDef = getLoggerDefine(jp, ApiLogger.class);
			String loggerContent = getSuccessLoggerContent(jp,loggerDef,retVal);
			if(loggerDef.console()){
				Subject sub = SecurityUtils.getSubject();
				if(sub.isAuthenticated()){
					Object user = sub.getPrincipal();
					WebDelegatingSubject webSub = (WebDelegatingSubject) sub;
					logger.info("{} 用户:{},IP:{}", loggerContent, user, webSub.getHost());
				}
				else{
					logger.info("{}",loggerContent);
				}
			}
			if(loggerDef.db() && loggerService != null){
				Subject sub = SecurityUtils.getSubject();
				String ip = "";
				UT user = null;
				if(sub.isAuthenticated()){
					user = UserUtils.getCurrentUser();
					WebDelegatingSubject webSub = (WebDelegatingSubject) sub;
					ip = webSub.getHost();
				}
				loggerService.addLog(loggerDef.cloudUser(),loggerDef.type(), loggerContent, user, ip, new Date());
			}
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	@AfterThrowing(pointcut="serviceAspect()",throwing="e")
	public void afterServiceAspectThrowable(JoinPoint jp, Throwable e){
		ApiLogger loggerDef;
		try {
			loggerDef = getLoggerDefine(jp, ApiLogger.class);
			String loggerContent = getExceptionLoggerContent(jp,loggerDef,e);
			if(loggerDef.file()){
				Subject sub = SecurityUtils.getSubject();
				if(sub.isAuthenticated()){
					UT user = UserUtils.getCurrentUser();
					WebDelegatingSubject webSub = (WebDelegatingSubject) sub;
					logger.info("{} 用户:{},IP:{}", loggerContent,user, webSub.getHost());
				}
				else{
					logger.info("{}",loggerContent);
				}
			}
			if(loggerDef.db() && loggerService != null){
				Subject sub = SecurityUtils.getSubject();
				String loginName = "";
				String ip = "";
				UT user = null;
				if(sub.isAuthenticated()){
					user = UserUtils.getCurrentUser();
					WebDelegatingSubject webSub = (WebDelegatingSubject) sub;
					ip = webSub.getHost();
				}
				loggerService.addLog(loggerDef.cloudUser(), loggerDef.type(),loggerContent, user, ip, new Date());
			}
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException e1) {
			e1.printStackTrace();
		}
	}


	private String getExceptionLoggerContent(JoinPoint jp,
											 ApiLogger logger, Throwable e) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		// 新方式
		if(StringUtils.isNotBlank(logger.request())) {
			Map<String, Object> params  = this.getAllParams(jp);
			params.put("exception", e);
			return parseEL(logger.request() + logger.error(), params);
		}
		Object[] args = jp.getArgs();
		ParamDefine[] pds = logger.paramProperties();
		Object[] msgParam = new Object[pds.length];
		for (int i = 0; i < msgParam.length; i++) {
			Object tmp = args[pds[i].paramIndex()];
			if(StringUtils.isBlank(pds[i].property())){
				msgParam[i] = tmp;
			}
			else{
				msgParam[i] = BeanUtils.getProperty(tmp, pds[i].property());
			}
		}

		String desc = String.format(logger.content(), msgParam);
		desc += "出现异常:" + e.getMessage();
		
		return desc;
	}

	/**
	 * 获取所有api方法的参数和登录用户信息
	 * @param jp
	 * @return
	 */
	private Map<String, Object> getAllParams(JoinPoint jp) {
		Map<String, Object> result = new HashMap<>(5);
		MethodSignature ms = (MethodSignature) jp.getSignature();
		String[] params = ms.getParameterNames();
		Object[] args = jp.getArgs();
		for(int i = 0; i < params.length; i++) {
			result.put(params[i], args[i]);
		}
		result.put("loginUser", UserUtils.getCurrentUser());
		return result;
	}
}
