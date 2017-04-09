package au.com.auspost.validation;

import com.google.common.primitives.Ints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PostcodeValidator implements ConstraintValidator<ValidPostcode, String> {

    public PostcodeValidator() {
    }

    @Override
    public void initialize(ValidPostcode constraint) {
    }

    @Override
    public boolean isValid(String queryString, ConstraintValidatorContext context) {
        return Ints.tryParse(queryString) != null && queryString.length() == 4;
    }
}
