package org.springframework.ws.samples.mtom.aspects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggingAspect {
	
	private static final Log logger = LogFactory.getLog(LoggingAspect.class);
	
	@Pointcut("within(org.springframework.ws.samples.mtom.service.*) ||"
			+ "within(org.springframework.ws.samples.mtom.ws.*)")
	public void insideAllClasses() {}
	
	
	@Before("insideAllClasses()")
    public void before(JoinPoint joinPoint) {
    	
		// xlitand: that's just aspect's code
		logger.info("BEFORE : " + 
				joinPoint.getTarget().getClass().getSimpleName() + 
				" " +
				joinPoint.getSignature().getName()
				);
		
		// xlitand: TODO: delete
		System.out.println("BEFORE : " + 
				joinPoint.getTarget().getClass().getSimpleName() + 
				" " +
				joinPoint.getSignature().getName()
				);
		
    }
    
	@AfterReturning("insideAllClasses()")
    public void afterReturning(JoinPoint joinPoint) {
    			
		logger.info("AFTER : " + 
				joinPoint.getTarget().getClass().getSimpleName() + 
				" " +
				joinPoint.getSignature().getName()
				);
		
		// xlitand: TODO: delete
		System.out.println("AFTER : " + 
				joinPoint.getTarget().getClass().getSimpleName() + 
				" " +
				joinPoint.getSignature().getName()
				);
    	
    }
}
