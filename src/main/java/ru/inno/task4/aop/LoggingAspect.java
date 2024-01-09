package ru.inno.task4.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.Arrays;

@Aspect
@Log4j2
public class LoggingAspect {
    @Around("@annotation(LogTransformation)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName()
                .substring(joinPoint.getSignature().getDeclaringTypeName().lastIndexOf(".") + 1);
        Object[] arguments = joinPoint.getArgs();
        long startTime = System.currentTimeMillis();

        log.info("Начало выполнения метода {} с параметрами {}"
                , className + "." + methodName, Arrays.asList(arguments));

        Object returnedByMethod = null;
        try {
            returnedByMethod = joinPoint.proceed();
        } catch (Throwable e) {
            log.error("Ошибка в методе {}: {}", methodName, e.getMessage());
        }

        long endTime = System.currentTimeMillis();
        long timeElapsed = (endTime - startTime);

        log.info("Метод выполнен за {} мсек. и возвратил {}", timeElapsed, returnedByMethod);

        return returnedByMethod;
    }
}
