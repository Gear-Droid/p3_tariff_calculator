package ru.fastdelivery.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
@Profile("!test")
public class LoggingAspect {

    @Before("@annotation(LoggableController)")
    public void logBefore(JoinPoint joinPoint) {
        var requestParams = Arrays.stream(joinPoint.getArgs())
                .toList()
                .toString();

        log.info(
                "Выполняется расчет цены по тарифному калькулятору с параметрами: {}",
                requestParams
        );
    }

    @AfterReturning(pointcut = "@annotation(LoggableController)", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("Результаты расчета цены по тарифному калькулятору: {}", result);
    }

    @AfterThrowing(pointcut = "@annotation(LoggableController)", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
        log.error("Ошибка при выполнении расчета цены по тарифному калькулятору! ", exception);
    }
}
