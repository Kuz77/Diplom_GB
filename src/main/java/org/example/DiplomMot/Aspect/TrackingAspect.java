package org.example.DiplomMot.Aspect;

import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
@Log
public class TrackingAspect {
    @Before("@annotation(org.example.DiplomMot.Aspect.TrackUserAction)")
    public Object logCallMethod(JoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        log.info("Пользователь: " + SecurityContextHolder.getContext().getAuthentication().getName()
                + " вызвал метод: " + method.getName() + " с параметрами: "
                + Arrays.toString(joinPoint.getArgs()));
        return joinPoint;
    }
}
