package com.coderman.common.aop;

import com.coderman.monitor.model.OperateWithBLOBs;
import com.coderman.monitor.service.OperateService;
import com.coderman.util.HttpUtil;
import com.coderman.util.ShiroContextHolder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @Author zhangyukang
 * @Date 2020/9/10 10:08
 * @Version 1.0
 **/
@Aspect
@Component
public class OperateAspect {

    @Autowired
    private OperateService operateService;

    /**
     * 此处的切点是注解的方式，也可以用包名的方式达到相同的效果
     * '@Pointcut("execution(* com.wwj.springboot.service.impl.*.*(..))")'
     */
    @Pointcut("@annotation(Operate)")
    public void operationLog() {
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 环绕增强，相当于MethodInterceptor
     */
    @Around("operationLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object res = null;
        OperateWithBLOBs operationLog = null;
        long startTime = System.currentTimeMillis();
        try {
            res = joinPoint.proceed();
            operationLog= getOperationLog(joinPoint, res, (System.currentTimeMillis() - startTime),1,null);
        }catch (Exception e){
            operationLog= getOperationLog(joinPoint, res, (System.currentTimeMillis() - startTime),2,e.getMessage());
            throw e;
        }finally {
            operateService.add(operationLog);
        }
        return res;
    }

    /**
     * 添加操作日志
     * @param joinPoint
     * @param res
     * @param time
     * @throws JsonProcessingException
     * @throws ClassNotFoundException
     */
    private OperateWithBLOBs getOperationLog(JoinPoint joinPoint, Object res, long time,int type,String errorMsg) throws JsonProcessingException, ClassNotFoundException {
        String targetName = joinPoint.getTarget().getClass().getName();
        HttpServletRequest request = ShiroContextHolder.getHttpServletRequest();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getDeclaredMethods();
        String module="";
        String operateDesc = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    // 操作说明
                    Operate annotation = method.getAnnotation(Operate.class);
                    if(annotation!=null){
                        operateDesc=annotation.operateDesc();
                        module=annotation.operateModule();
                        break;
                    }
                }
            }
        }
        OperateWithBLOBs operate=new OperateWithBLOBs();
        operate.setController(targetName.substring(targetName.lastIndexOf(".")+1));
        operate.setMethod(methodName+" ()");
        operate.setOperation("["+module+"]=>"+operateDesc);
        operate.setTime(time);
        operate.setIp(HttpUtil.getIpAddr(request));
        operate.setLocation(HttpUtil.getCityInfo(request));
        operate.setUsername(ShiroContextHolder.getUsername());
        operate.setCreateTime(new Date());
        operate.setType(type);
        operate.setErrorMsg(errorMsg);
        operate.setParams(objectMapper.writeValueAsString(joinPoint.getArgs()));
        operate.setReturnValue(objectMapper.writeValueAsString(res));
        return operate;
    }

}
