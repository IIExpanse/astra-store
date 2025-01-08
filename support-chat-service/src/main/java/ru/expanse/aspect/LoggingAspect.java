package ru.expanse.aspect;

import lombok.extern.log4j.Log4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j
public class LoggingAspect {

    @Pointcut("execution(public * ru.expanse.controller.*.*(..))")
    public void controllerMethod() {}

    @Before("controllerMethod()")
    public void beforeControllerMethod(JoinPoint joinPoint) {}

    @After("controllerMethod()")
    public void afterControllerMethod(JoinPoint joinPoint) {}

    @Pointcut("execution(public * ru.expanse.dao.adapter.*.*(..))")
    public void adapterMethod() {}

    @After("adapterMethod()")
    public void afterAdapterMethod(JoinPoint joinPoint) {}
}
