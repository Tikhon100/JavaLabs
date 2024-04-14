package com.example.phonenumbersapi.aspect;

import com.example.phonenumbersapi.counter.RequestCounter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class RequestCounterAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("@annotation(com.example.phonenumbersapi.aspect.Counting)")
    public void methodsWithRequestCounterAnnotation() {

    }

    @Before("methodsWithRequestCounterAnnotation()")
    public void requestCounterIncrementAndLogIt(final JoinPoint joinPoint) {
        RequestCounter.increment();
        LOGGER.info("Increment requestCounter from {}.{}()." + "Current value of requestCounter is {}",
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
                RequestCounter.getCount());
    }
}