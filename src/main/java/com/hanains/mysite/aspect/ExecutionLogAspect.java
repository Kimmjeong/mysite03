package com.hanains.mysite.aspect;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionLogAspect {
	
	@Around("execution(* *..dao.*.*(..))")
	public Object around(ProceedingJoinPoint pjp) throws Throwable{
		
		// 클래스 정보
		MethodSignature methodSignature= (MethodSignature) pjp.getSignature();
		// 메서드 정보
		Method method= methodSignature.getMethod();
		
		Log LOG = LogFactory.getLog(methodSignature.getDeclaringType());
		
		LOG.info("[start: "+methodSignature.getDeclaringType()+"] "+method.getName());
		
		Object result=pjp.proceed();
		
		LOG.info("[end: "+methodSignature.getDeclaringType()+"] "+method.getName());
		
		return result;
		
	}
}
