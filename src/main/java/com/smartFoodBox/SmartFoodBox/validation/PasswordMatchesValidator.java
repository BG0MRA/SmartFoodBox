package com.smartFoodBox.SmartFoodBox.validation;

import com.smartFoodBox.SmartFoodBox.model.dto.UserRegistrationDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserRegistrationDTO> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        //TODO:Initialization code if necessary
    }

    @Override
    public boolean isValid(UserRegistrationDTO dto, ConstraintValidatorContext context) {
        return dto.getPassword().equals(dto.getConfirmPassword());
    }
}
