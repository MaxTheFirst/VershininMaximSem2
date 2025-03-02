package com.example.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

  @Before("execution( * com.example.demo.controller.impl.*.*( .. ))")
  public void logBefore(JoinPoint joinPoint){
    log.debug("{} will de calling", joinPoint.getSignature().getName());
  }

  @Around("execution( * com.example.demo.controller.impl.*.*( .. ))")
  public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    Instant start = Instant.now();
    Object result = joinPoint.proceed();
    long duration = Instant.now().toEpochMilli() - start.toEpochMilli();
    log.debug("The method {} was completed in {} milliseconds", joinPoint.getSignature().getName(), duration);
    return result;
  }
}