package com.roger.curs.springboot.app.aop.springboot_aop.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Order(2)
@Component
@Aspect
public class GreetingFooAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("GreetingServicePointcuts.greetingFooAspectPointcut()")
    public void loggerBefore(JoinPoint joinPoint) {

        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        logger.info("FOO Antes: {} con los argumentos {}", method, args);

    }

}
