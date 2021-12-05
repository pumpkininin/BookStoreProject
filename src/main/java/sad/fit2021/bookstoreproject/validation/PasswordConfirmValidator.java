package sad.fit2021.bookstoreproject.validation;

import sad.fit2021.bookstoreproject.model.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConfirmValidator implements ConstraintValidator<PasswordConfirm, Object> {

    @Override
    public void initialize(final PasswordConfirm constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final UserDto user = (UserDto) obj;
        return user.getPassword().equals(user.getConfirmPassword());
    }

}