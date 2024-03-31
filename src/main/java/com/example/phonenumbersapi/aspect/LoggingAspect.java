package com.example.phonenumbersapi.aspect;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("@annotation(com.example.phonenumbersapi.aspect.Logged)")
    private void allServiceMethods() {
    }

    @Before("allServiceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        if (logger.isInfoEnabled()) {
            logger.info("Method: {}(), args: {}", methodName, Arrays.toString(args));
        }
    }

    @AfterReturning(pointcut = "allServiceMethods()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Method: {}(), status: {}", methodName, result);
    }

    @AfterThrowing(pointcut = "allServiceMethods()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        logger.error("Exception in method: {}(), message: {}", methodName, exception.getMessage());
    }

    @PostConstruct
    public void initAspect() {
        logger.info("Aspect is initialized");
    }

    @PreDestroy
    public void destroyAspect() {
        logger.info("Aspect is destroyed");
    }
}