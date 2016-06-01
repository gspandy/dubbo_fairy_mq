package com.fairy.mq.util;

import com.fairy.mq.exception.ValidateException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * @Author andongxu
 * @Create time 16-2-29:下午5:25
 * @Version
 * @Last update time
 */
public class ValidatorUtil {
    private static Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public static <T> void validate(T t) throws ValidateException {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
        if(constraintViolations.size() > 0) {
            String validateError = "";
            for(ConstraintViolation<T> constraintViolation: constraintViolations) {
                validateError += constraintViolation.getPropertyPath();
                validateError += ":" + constraintViolation.getMessage() + ";";

            }
            throw new ValidateException(validateError);
        }
    }
}
