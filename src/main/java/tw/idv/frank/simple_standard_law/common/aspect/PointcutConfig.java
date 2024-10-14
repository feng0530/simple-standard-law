package tw.idv.frank.simple_standard_law.common.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PointcutConfig {

    @Pointcut("execution(* tw.idv.frank.simple_standard_law.schema..*(..))")
    public void schema() {

    }
}
