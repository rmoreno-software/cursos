package com.roger.curs.springboot.app.aop.springboot_aop.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class GreetingAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.roger.curs.springboot.app.aop.springboot_aop.services.*.*(..))")
    public void loggerBefore(JoinPoint joinPoint) {

        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        logger.info("Antes: " + method + " con los argumentos " + args);

    }

    @After("execution(* com.roger.curs.springboot.app.aop.springboot_aop.services.*.*(..))")
    public void loggerAfter(JoinPoint joinPoint) {

        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        logger.info("Después: " + method + " con los argumentos " + args);

    }

    @AfterReturning("execution(* com.roger.curs.springboot.app.aop.springboot_aop.services.*.*(..))")
    public void loggerAfterReturning(JoinPoint joinPoint) {

        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        logger.info("Después de retornar: " + method + " con los argumentos " + args);

    }

    @AfterThrowing("execution(* com.roger.curs.springboot.app.aop.springboot_aop.services.*.*(..))")
    public void loggerAfterThrowing(JoinPoint joinPoint) {

        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        logger.info("Después de lanzar excepción: " + method + " con los argumentos " + args);

    }
}
