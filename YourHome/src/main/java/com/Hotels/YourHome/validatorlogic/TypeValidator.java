package com.Hotels.YourHome.validatorlogic;

import com.Hotels.YourHome.validators.Type;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class TypeValidator implements ConstraintValidator<Type, String> {

    private static final String[] types = {"double","single","queen","king","triple"};
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        return Arrays.asList(types).contains(s);
    }
}
