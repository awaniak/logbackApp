package io.github.awaniak.logbackApp.commons.logging;


import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class LoggingAspect {

    private final LeefLoggingService leefLoggingService;

    public LoggingAspect(LeefLoggingService leefLoggingService) {
        this.leefLoggingService = leefLoggingService;
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("within(io.github.awaniak.logbackApp.controller..*)")
    public void controllerPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice
     * @throws Throwable throws IllegalArgumentException
     */
    @Before("controllerPointcut()")
    public void logAroundController(JoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = (HttpServletRequest) Arrays.stream(joinPoint.getArgs()).filter(o -> o instanceof HttpServletRequest).findAny().orElse(RuntimeException.class);
        leefLoggingService.log(request, joinPoint.getSignature().getName(), "odic-f");
    }

}
