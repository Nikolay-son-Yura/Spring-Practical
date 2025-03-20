package ru.gb.aspect.recover;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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

    @AfterThrowing(value = "recoverPointcut", throwing = "exc")
    public Object exception(ProceedingJoinPoint proceedingJoinPoint, Exception exception) throws Throwable {
        Method method = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();


//        String methodName=proceedingJoinPoint.toShortString().replaceAll("execution","");


        if (method.isAnnotationPresent(Recover.class)) { //если представлена
            List<Class<?>> noRecoverFor = properties.getNoRecoverFor(); //из настойки .yaml
            List<Class<?>> ignoredClasses = Arrays.stream(method.getAnnotation(Recover.class).noRecovered()).toList();
            Set<Class<?>> resultList = new HashSet<>(noRecoverFor);

            boolean replaceList = method.getAnnotation(Recover.class).replaceDefaultList();
            if (!replaceList) {
                resultList.addAll(ignoredClasses);
            }

//              FIXME этот вывод чисто для галочки, чтобы посмотреть результирующий вариант спика классов
//            resultList.forEach(el -> System.out.println(el.getName()));

            log.error("Исключение -->> {}", exception.getClass().getName());
            log.error("-->> {}", exception.getMessage()); //оригинальное сообщение, увидим в консоли
            if (resultList.contains(exception.getClass())) {
                throw new RuntimeException("Исключение " + exception.getClass().getName() + " не обрабатывается");
            }
            if (method.getReturnType().isPrimitive()) {

                Class<?> type = method.getReturnType();
                Object defaultValue = getDefaultValue(type);
                log.error("return default primitive value ->> {}", defaultValue);

                return defaultValue;
            }

            log.error("return null");
            return null;
        } else {
            throw exception;
        }
    }

    private Object getDefaultValue(Class<?> returnType) {
        if (returnType == boolean.class) return false;
        if (returnType == char.class) return '\u0000';
        if (returnType == byte.class) return (byte) 0;
        if (returnType == short.class) return (short) 0;
        if (returnType == int.class) return 0;
        if (returnType == long.class) return 0L;
        if (returnType == float.class) return 0.0f;
        if (returnType == double.class) return 0.0d;
        return null;
    }

}
