package com.zhu.demo.aop;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.zhu.demo.entity.Log;
import com.zhu.demo.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class LogAspect {
    @Autowired
    private LogService logService;

    @Pointcut("@annotation(com.zhu.demo.aop.LogOption)")
    public void logPointCut() {

    }
    @Before(value = "logPointCut()")
    public void beforeLog(){
        log.info("===========注解 @LogOption 前置通知============");
    }

    @Pointcut("execution(public * com.zhu.demo.controller..*.*(..))")
    public void webLog(){

    }
    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint 切入点
     * @param keys      返回结果
     */
    @AfterReturning(value = "webLog()", returning = "keys")
    public void saveLog(JoinPoint joinPoint, Object keys) throws Exception {
        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取切入点所在的方法
        Method method = signature.getMethod();
        LogOption opLog = method.getAnnotation(LogOption.class);
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        Log log = new Log();
        try{
            log.setType(request.getMethod());
            log.setTitle(request.getQueryString());
            log.setMethod(request.getRequestURI());
            log.setParams(request.getParameterMap().toString());
            log.setCreateBy(request.getRemoteUser());
            log.setCreateDate(new Date());
            logService.add(log);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 转换request 请求参数
     *
     * @param paramMap request获取的参数数组
     */
    public Map<String, String> converMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        for (String key : paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;

    }
}
