package com.smartFoodBox.SmartFoodBox.service;

import com.smartFoodBox.SmartFoodBox.model.dto.UserRegistrationDTO;
import com.smartFoodBox.SmartFoodBox.model.enums.UserRoleEnum;
import com.smartFoodBox.SmartFoodBox.model.user.SmartFoodBoxUserDetails;

import java.util.Optional;

public interface UserService {

    void registerUser(UserRegistrationDTO userRegistration);

    Optional<SmartFoodBoxUserDetails> getCurrentUser();

    boolean emailExists(String email);

    void assignRoleToUser(Long userId, UserRoleEnum role);

}
