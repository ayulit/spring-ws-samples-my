package org.springframework.ws.samples.mtom.aspects;

import org.apache.log4j.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggingAspect {
	
	private static final Logger LOG = Logger.getLogger(LoggingAspect.class);
	
	@Pointcut("within(org.springframework.ws.samples.mtom.service.*)")
	public void insideService() {}
	
	
	@Before("insideService()")
    public void before(JoinPoint joinPoint) {
    	
		// xlitand: that's just aspect's code
		LOG.info("BEFORE : " + 
				joinPoint.getTarget().getClass().getSimpleName() + 
				" " +
				joinPoint.getSignature().getName()
				);
		
    }
    
	@AfterReturning("insideService()")
    public void afterReturning(JoinPoint joinPoint) {
    			
		LOG.info("AFTER : " + 
				joinPoint.getTarget().getClass().getSimpleName() + 
				" " +
				joinPoint.getSignature().getName()
				);
    	
    }
}
