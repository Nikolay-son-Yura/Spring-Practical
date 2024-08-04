package ru.gb.Lesson8.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class RecoverAspect {
// тут еще подумаю
    @Pointcut("execution(* ru.gb.Lesson8.service.*.*(..))")
    public void recoverPointcut() {
    }

    @Around(value = "recoverPointcut")
    public Object exception(ProceedingJoinPoint proceedingJoinPoint)throws Throwable{
        String methodName=proceedingJoinPoint.toShortString().replaceAll("execution","");

        try {
            return proceedingJoinPoint.proceed();
        }catch (Exception e){
            log.info("exception -> {}",methodName);
            return e.toString();

        }

    }

}
