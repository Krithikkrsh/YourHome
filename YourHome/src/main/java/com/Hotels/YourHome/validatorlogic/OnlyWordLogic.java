package com.Hotels.YourHome.validatorlogic;

import com.Hotels.YourHome.customException.BusinessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Aspect
@Component
public class OnlyWordLogic {


    @Around("@annotation(com.Hotels.YourHome.validators.OnlyWords)")
    public Object OnlyWord(ProceedingJoinPoint joinPoint) throws Throwable{

        String name = (String) joinPoint.getArgs()[0];

        if (name==null || name.isEmpty() || !(Pattern.matches("^[a-zA-Z]*$",name))) {
            throw new BusinessException(404,"Field is not in Valid Manner!");
        }
        Object result = joinPoint.proceed();
        return result;
    }
}
