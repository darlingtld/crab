package createsh.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by darlingtld on 2015/8/6 0006.
 */
@Aspect
public class MethodAdvice {

    private Logger logger = LoggerFactory.getLogger(Method.class);

    @Pointcut("execution(* createsh.controller.*.*(..)) || execution(* createsh.service.*.*(..))")
    private void aspectjMethod() {
    }

    @Around(value = "aspectjMethod()")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();

        Object retVal = pjp.proceed();

        logger.info(String.format("[%s] spent %sms", pjp.getSignature().getName(), System.currentTimeMillis() - start));
        return retVal;
    }
}
