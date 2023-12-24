package restoran.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import restoran.valid.validation.PasswordValidation;
public class PasswordValidator implements ConstraintValidator<PasswordValidation,String > {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.length()>=4;
    }
}
