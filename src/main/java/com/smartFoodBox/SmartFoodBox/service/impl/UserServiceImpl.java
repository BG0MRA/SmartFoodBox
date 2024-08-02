package com.smartFoodBox.SmartFoodBox.service.impl;

import com.smartFoodBox.SmartFoodBox.model.dto.UserRegistrationDTO;
import com.smartFoodBox.SmartFoodBox.model.entity.UserEntity;
import com.smartFoodBox.SmartFoodBox.model.user.SmartFoodBoxUserDetails;
import com.smartFoodBox.SmartFoodBox.repository.UserRepository;
import com.smartFoodBox.SmartFoodBox.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserServiceImpl(ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    @Override
    public void registerUser(UserRegistrationDTO userRegistration) {
        userRepository.save(map(userRegistration));
    }

    @Override
    public Optional<SmartFoodBoxUserDetails> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication !=null &&
        authentication.getPrincipal() instanceof SmartFoodBoxUserDetails smartFoodBoxUserDetails) {
            return Optional.of(smartFoodBoxUserDetails);
        }
        return Optional.empty();
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }


    private UserEntity map(UserRegistrationDTO userRegistrationDTO) {
        UserEntity mappedEntity = modelMapper.map(userRegistrationDTO, UserEntity.class);

        mappedEntity.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));

        return mappedEntity;
    }
}
