package com.henu.ocr.aspect;

import com.henu.ocr.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class TimeMonitorAspect {

    @Pointcut("execution(* com.henu.ocr.controller..*.*(..))")
    public void allControllerMethods() {}

    @Pointcut("@annotation(com.henu.ocr.annotation.Timed)")
    public void annotatedMethods() {}

    // 组合切点：监控所有Controller或带注解的Service方法
    @Pointcut("allControllerMethods() || annotatedMethods()")
    public void monitorPointcut() {}

    @Around("monitorPointcut()")
    public Object monitorExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();

        // 判断是否包含自定义注解
        boolean hasAnnotation = signature.getMethod().isAnnotationPresent(Timed.class);

        long start = System.currentTimeMillis();
        try {
            Object result = pjp.proceed();

            if (hasAnnotation) {
                long duration = System.currentTimeMillis() - start;
                log.info("方法 {} 执行耗时: {}ms | 参数: {}",
                        signature.toShortString(),
                        duration,
                        Arrays.toString(pjp.getArgs()));
            }
            return result;
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - start;
            log.error("方法 {} 执行失败 | 耗时: {}ms | 异常: {}",
                    signature.getName(),
                    duration,
                    e.getClass().getSimpleName());
            throw e;
        } finally {
            long duration = System.currentTimeMillis() - start;
            log.debug("[基础监控] {} 执行完成 | 耗时: {}ms",
                    pjp.getSignature().toShortString(),
                    duration);
        }
    }
}
