package com.Hotels.YourHome.validatorlogic;

import com.Hotels.YourHome.validators.PhoneNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, Long> {

    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {

        String s = String.valueOf(aLong);
        if (s==null || s.isEmpty())
            return false;

        if (Pattern.matches("^(\\+\\d{1,2}\\s?)?1?\\-?\\.?\\s?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$",s))
            return true;
        else
            return false;
    }
}
