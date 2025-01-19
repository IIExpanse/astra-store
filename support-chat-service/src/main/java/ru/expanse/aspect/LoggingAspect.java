package ru.expanse.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    private static final String CALLED = "called";
    private static final String COMPLETED = "completed";

    @Pointcut("execution(public * ru.expanse.controller.*.*(..))")
    public void controllerMethod() {
    }

    @Before("controllerMethod()")
    public void beforeControllerMethod(JoinPoint joinPoint) {
        doLog(joinPoint, Level.INFO, CALLED);
    }

    @After("controllerMethod()")
    public void afterControllerMethod(JoinPoint joinPoint) {
        doLog(joinPoint, Level.INFO, COMPLETED);
    }

    @Pointcut("execution(public * ru.expanse.dao.adapter.*.*(..))")
    public void adapterMethod() {
    }

    @After("adapterMethod()")
    public void afterAdapterMethod(JoinPoint joinPoint) {
        doLog(joinPoint, Level.TRACE, COMPLETED);
    }

    private void doLog(JoinPoint joinPoint, Level level, String action) {
        switch (level) {
            case Level.INFO -> log.info(getMessage(joinPoint, action));
            case Level.TRACE -> log.trace(getMessage(joinPoint, action));
        }
    }

    private String getMessage(JoinPoint joinPoint, String action) {
        return String.format("Method %s: [%s], with arguments: [%s]",
                action,
                joinPoint.getSignature().toShortString(),
                Arrays.toString(joinPoint.getArgs()));
    }
}
