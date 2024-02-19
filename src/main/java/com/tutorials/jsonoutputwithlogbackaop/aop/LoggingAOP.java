package com.tutorials.jsonoutputwithlogbackaop.aop;


import com.tutorials.jsonoutputwithlogbackaop.exception.CustomException;
import com.tutorials.jsonoutputwithlogbackaop.model.LogDetails;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAOP {
    private static final Logger log = LoggerFactory.getLogger(LoggingAOP.class);

    @Before(value = "execution(* com.tutorials.jsonoutputwithlogbackaop.controller.*.*(..)) " +
            "|| execution(* com.tutorials.jsonoutputwithlogbackaop.service.*.*(..))")
    public void beforeAll(JoinPoint joinPoint) {
        var methodSignature = (MethodSignature) joinPoint.getSignature();

        log.info("{}",
                LogDetails.ofStart(
                        methodSignature.getDeclaringType().getSimpleName(),
                        methodSignature.getMethod().getName(),
                        getParameters(joinPoint)));
    }

    @Before(value = "execution(* com.tutorials.jsonoutputwithlogbackaop.handler.*.*(..)) ")
    public void beforeExceptionHandler(JoinPoint joinPoint) {
        log.error("{}", LogDetails.ofError(getParameters(joinPoint)));
    }

    @AfterReturning(value = "execution(* com.tutorials.jsonoutputwithlogbackaop.controller.*.*(..)) " +
            "|| execution(* com.tutorials.jsonoutputwithlogbackaop.service.*.*(..))", returning = "output")
    public void afterAll(JoinPoint joinPoint, Object output) {
        var methodSignature = (MethodSignature) joinPoint.getSignature();
        var parameters = getParameters(joinPoint);

        log.info("{}", LogDetails.ofEnd(methodSignature.getDeclaringType().getSimpleName(),
                methodSignature.getMethod().getName(), parameters, output));
    }

    private Map<String, Object> getParameters(JoinPoint joinPoint) {
        var parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        var parameterValues = joinPoint.getArgs();

        return IntStream.range(0, parameterNames.length).boxed()
                .collect(Collectors.toMap(
                        i -> parameterNames[i],
                        i -> extractParameterValue(parameterValues[i])));
    }

    private Object extractParameterValue(Object parameterValue) {
        return parameterValue instanceof Exception exception
                ? extractExceptionData(exception)
                : Objects.requireNonNullElse(parameterValue, "null");
    }

    private Map<String, Object> extractExceptionData(Exception exception) {
        var exceptionDetails = new HashMap<String, Object>();

        exceptionDetails.put("exceptionType", exception.getClass().getName());
        exceptionDetails.put("message", exception.getMessage());
        exceptionDetails.put("localizedMessage", exception.getLocalizedMessage());

        if (exception instanceof CustomException) {
            exceptionDetails.put("className", exception.getStackTrace()[0].getClassName());
            exceptionDetails.put("methodName", exception.getStackTrace()[0].getMethodName());
            exceptionDetails.put("lineNumber", exception.getStackTrace()[0].getLineNumber());
        }
        return exceptionDetails;
    }
}
