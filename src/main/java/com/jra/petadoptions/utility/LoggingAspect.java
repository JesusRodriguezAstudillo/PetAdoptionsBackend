package com.jra.petadoptions.utility;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
	
	@AfterThrowing(pointcut="execution(* com.jra.petadoptions.repositoty.*DAO.*(..))", throwing="exception")
	public void logDAOException(Exception exception) {
		logger.error(exception.getMessage());
	}
	
	@AfterThrowing(pointcut="execution(* com.jra.petadoptions.service.*Impl.*(..))", throwing="exception")
	public void logServiceException(Exception exception) {
		logger.error(exception.getMessage());
	}
	
	@AfterThrowing(pointcut="execution(* com.jra.petadoptions.controller.*Controller.*(..))", throwing="exception")
	public void logControllerException(Exception exception) {
		logger.error(exception.getMessage());
	}
}
