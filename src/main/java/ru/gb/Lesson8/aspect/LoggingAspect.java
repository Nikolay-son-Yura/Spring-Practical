package ru.gb.Lesson8.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
    @Pointcut("execution(* ru.gb.Lesson8.service.*.*(..))")
    public void timesheetServiceMethodsPointcut() {
    }

//    @Pointcut("execution(* ru.gb.Lesson8.service.*.*(..))")
//    public void projectServiceMethodsPointcut() {
//    }

    @Before(value = "timesheetServiceMethodsPointcut()")
    public void beforeTimesheetServiceFindById(JoinPoint jp) {
//        String type=jp.getArgs().getClass().getSimpleName();

        String methodName=jp.toShortString().replaceAll("execution","");
        String type = null;
        Object[] arg = jp.getArgs();
        for (Object object : arg) {
            type = object.getClass().getSimpleName();
        }
        if (arg.length>0) {
            log.info("Before -> {}{}={}",methodName, type, arg);
        } else {
            log.info("Before -> {}", methodName);
        }
    }
}
