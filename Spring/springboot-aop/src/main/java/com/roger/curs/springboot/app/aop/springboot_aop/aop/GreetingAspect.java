package com.roger.curs.springboot.app.aop.springboot_aop.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Order(1)
@Aspect
@Component
public class GreetingAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("GreetingServicePointcuts.greetingLoggerPointcut()")
    public void loggerBefore(JoinPoint joinPoint) {

        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        logger.info("Antes: " + method + " con los argumentos " + args);

    }

    @After("GreetingServicePointcuts.greetingLoggerPointcut()")
    public void loggerAfter(JoinPoint joinPoint) {

        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        logger.info("Después: " + method + " con los argumentos " + args);

    }

    @AfterReturning("GreetingServicePointcuts.greetingLoggerPointcut()")
    public void loggerAfterReturning(JoinPoint joinPoint) {

        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        logger.info("Después de retornar: " + method + " con los argumentos " + args);

    }

    @AfterThrowing("GreetingServicePointcuts.greetingLoggerPointcut()")
    public void loggerAfterThrowing(JoinPoint joinPoint) {

        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        logger.info("Después de lanzar excepción: " + method + " con los argumentos " + args);

    }

    @Around("GreetingServicePointcuts.greetingLoggerPointcut()")
    public Object loggerArround(ProceedingJoinPoint proceedingJoinPoint) {
        String method = proceedingJoinPoint.getSignature().getName();
        String args = Arrays.toString(proceedingJoinPoint.getArgs());

        Object result = null;
        try {
            logger.info("El metodo " + method + "() con los parametros " + args);
            result = proceedingJoinPoint.proceed();
            logger.info("El metodo " + method + "() retorna el resultado " + result);
            return result;
        } catch (Throwable e) {
            logger.error("Error en la llamada del método " + method + "()");
            throw new RuntimeException(e);
        }

    }
}
