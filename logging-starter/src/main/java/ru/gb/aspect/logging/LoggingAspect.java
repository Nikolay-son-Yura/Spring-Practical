package ru.gb.aspect.logging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


@Slf4j
@Aspect
@RequiredArgsConstructor
public class LoggingAspect {

    private final LoggingProperties properties;

    @Pointcut("@annotation(ru.gb.aspect.logging.Logging)") // method
    public void loggingMethodsPointcut() {}

    @Pointcut("@within(ru.gb.aspect.logging.Logging)") // class
    public void loggingTypePointcut() {}



    @Around(value = "loggingMethodsPointcut() || loggingTypePointcut()")
    public Object loggingMethod(ProceedingJoinPoint pjp) throws Throwable {

        String methodName = pjp.toShortString().replaceAll("execution", "");

        String type = null;
        Object[] arg = pjp.getArgs();
        for (Object object : arg) {
            type = object.getClass().getSimpleName();
        }
        
        try {
            return pjp.proceed();
        } finally {
            if (arg.length > 0) {
                log.atLevel(properties.getLevel()).log("Before NEW LOGGING -> {},{}={})", methodName, type, arg);
            } else {
                log.atLevel(properties.getLevel()).log("Before NEW LOGGING -> {}", methodName);
            }
        }
    }
}
