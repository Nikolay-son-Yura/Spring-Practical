package ru.gb.aspect.recover;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;


/**
 * Создать аспект, который аспектирует методы, помеченные аннотацией Recover, и делает следующее:
 * * 2.1 Если в процессе исполнения метода был exception (любой),
 * * то его нужно залогировать ("Recovering TimesheetService#findById after Exception[RuntimeException.class, "exception message"]")
 * * и вернуть default-значение наружу Default-значение: для примитивов значение по умолчанию, для ссылочных типов - null.
 * * Для void-методов возвращать не нужно.
 */

@Slf4j
@Aspect
@RequiredArgsConstructor
public class RecoverAspect {
    private final RecoverProperties properties;

    @Pointcut("@annotation(ru.gb.recover.aspect.Recover)")
    public void recoverPointcut() {
    }

    @Around(value = "recoverPointcut")
    public void exception(ProceedingJoinPoint proceedingJoinPoint, Exception exception) throws Throwable {
        Method method = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();


//        String methodName=proceedingJoinPoint.toShortString().replaceAll("execution","");
////        Object target= proceedingJoinPoint.getTarget();
////         method = proceedingJoinPoint.getSignature().
//
//        try {
//            return proceedingJoinPoint.proceed();
//        }catch (Exception e){
//            log.info("exception -> {}",methodName);
//            return e.toString();
//
//        }

    }

}
