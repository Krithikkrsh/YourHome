package com.Hotels.YourHome.validatorlogic;

import com.Hotels.YourHome.validators.Remove;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class RemoveValidator implements ConstraintValidator<Remove,String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        if (s==null)
            return true;

        if ((!s.isEmpty()) ||Pattern.matches("[a-zA-Z0-9]+",s))
            return false;

        else
            return true;
    }
}
