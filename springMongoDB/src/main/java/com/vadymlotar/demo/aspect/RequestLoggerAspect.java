package com.vadymlotar.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.vadymlotar.demo.domain.model.ResponseItem;
import com.vadymlotar.demo.domain.service.ResponseItemService;

/**
 * Allows to log all incoming requests. Put information about duration of the
 * request to mongoDB.
 * 
 * @author vlotar
 * 
 */
@Component
@Aspect
public class RequestLoggerAspect {
	
	@Autowired
	private ResponseItemService responseItemService;

	/**
	 * Advice which allows do some operations with logging before and after
	 * method execution
	 * 
	 * @param joinPoint
	 *            joinPoint object describing the calling method
	 * @return result of method execution
	 * @throws Throwable
	 *             an exception could be thrown
	 */
	@Around("execution(* com.vadymlotar.demo.controller..MainController*.*(..))")
	public Object logTimeMethod(ProceedingJoinPoint joinPoint) throws Throwable {
		// execution of method started
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		Object retVal = joinPoint.proceed();
		// execution of method finished
		stopWatch.stop();
		// prepare string which will appear in the log
		StringBuffer logMessage = new StringBuffer();
		logMessage.append(joinPoint.getTarget().getClass().getName());
		logMessage.append(".");
		logMessage.append(joinPoint.getSignature().getName());
		logMessage.append("(");
		// append args
		Object[] args = joinPoint.getArgs();
		for (int i = 0; i < args.length; i++) {
			logMessage.append(args[i]).append(",");
		}
		if (args.length > 0) {
			logMessage.deleteCharAt(logMessage.length() - 1);
		}

		logMessage.append(")");
		logMessage.append(" execution time: ");
		logMessage.append(stopWatch.getTotalTimeMillis());
		logMessage.append(" ms");
		System.out.println(logMessage.toString());

		ResponseItem responseItem = new ResponseItem();
		responseItem.setRequestDuration(stopWatch.getTotalTimeMillis());
		responseItemService.addResponseItem(responseItem);
		
		return retVal;
	}

}

