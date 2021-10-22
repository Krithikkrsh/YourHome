package com.Hotels.YourHome.validators;

import com.Hotels.YourHome.validatorlogic.RemoveValidator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = RemoveValidator.class)
public @interface Remove {

    String field() default "field";
    String message() default "Please remove {field}";

    java.lang.Class<?>[] groups() default {};

    java.lang.Class<? extends javax.validation.Payload>[] payload() default {};
}
