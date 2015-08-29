package crab.advice;

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
public class StatAdvice {
    private Logger logger = LoggerFactory.getLogger(Method.class);

    static long hits = 0;

    @Pointcut("execution(* crab.controller.ProductController.*(..))")
    private void aspectjMethod() {
    }

    @Around(value = "aspectjMethod()")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        hits++;

        Object retVal = pjp.proceed();

        if (hits % 20 == 0) {
            logger.info(String.format("[Hits on crab] %s", hits));
        }
        return retVal;
    }
}
