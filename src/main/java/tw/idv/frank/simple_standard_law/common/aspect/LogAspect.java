package tw.idv.frank.simple_standard_law.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAspect {
    @Pointcut("execution(* tw.idv.frank.simple_standard_law.schema..*(..))")
    public void schema() {

    }

    @Around("schema()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info("[{}].[{}] is start with arguments: {}", className, methodName, args);

        long start = System.currentTimeMillis();

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("{} threw exception: {}", methodName, throwable.getMessage());
            throw throwable;
        }

        long duration = System.currentTimeMillis() - start;
        log.info("[{}].[{}] returned: {}", className, methodName, result);
        log.info("[{}].[{}] executed in {} ms", className, methodName, duration);

        return result;
    }

//    @Before("execution(* tw.idv.frank.simple_standard_law.schema..*(..))")
//    public void before(JoinPoint joinPoint) {
//        String className = joinPoint.getTarget().getClass().getName();
//        String methodName = joinPoint.getSignature().getName();
//        Object[] args = joinPoint.getArgs();
//        log.info("[{}].[{}] Start with arguments: {}...", className, methodName, args);
//    }
//
//    @After("execution(* tw.idv.frank.simple_standard_law.schema..*(..))")
//    public void after(JoinPoint joinPoint) {
//        String className = joinPoint.getTarget().getClass().getName();
//        String methodName = joinPoint.getSignature().getName();
//        log.info("[{}.{}] End...", className, methodName);
//    }
}
