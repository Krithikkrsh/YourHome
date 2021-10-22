package com.Hotels.YourHome.validatorlogic;

import com.Hotels.YourHome.validators.Password;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class PasswordValidator implements ConstraintValidator<Password,String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {

        log.info("Password from user: "+password);

        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";


        Pattern p = Pattern.compile(regex);


        if (password == null) {
            return false;
        }


        Matcher m = p.matcher(password);

        return m.matches();
    }
}
