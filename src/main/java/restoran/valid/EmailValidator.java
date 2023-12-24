package restoran.valid;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import restoran.valid.validation.EmailValidation;

public class EmailValidator implements ConstraintValidator<EmailValidation,String > {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.endsWith("@gmail.com") ;
    }
}
