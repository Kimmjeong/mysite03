package com.hanains.mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class ExecutionTimeAspect {
	
	//@Around("execution(* *..dao.*.*(..)) || execution(* *..service.*.*(..))")
	public Object around(ProceedingJoinPoint pjp) throws Throwable{
		
		String taskName=pjp.getTarget().getClass()+"."+pjp.getSignature().getName();
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start(taskName);
		
		Object result=pjp.proceed();
		
		stopWatch.stop();
		System.out.println("[Execution Time]["+taskName+"]"+stopWatch.getTotalTimeMillis()+" millis");
		
		return result;
	}

}
