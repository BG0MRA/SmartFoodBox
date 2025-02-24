package com.smartFoodBox.SmartFoodBox.service.impl;

import com.smartFoodBox.SmartFoodBox.model.dto.ProfileDTO;
import com.smartFoodBox.SmartFoodBox.model.dto.UserRegistrationDTO;
import com.smartFoodBox.SmartFoodBox.model.entity.UserEntity;
import com.smartFoodBox.SmartFoodBox.model.entity.UserRoleEntity;
import com.smartFoodBox.SmartFoodBox.model.enums.UserRoleEnum;
import com.smartFoodBox.SmartFoodBox.model.user.SmartFoodBoxUserDetails;
import com.smartFoodBox.SmartFoodBox.repository.UserRepository;
import com.smartFoodBox.SmartFoodBox.repository.UserRoleRepository;
import com.smartFoodBox.SmartFoodBox.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final UserRoleRepository userRoleRepository;

    public UserServiceImpl(ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           UserRoleRepository userRoleRepository) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void registerUser(UserRegistrationDTO userRegistration) {
        // 1. Convert DTO to entity
        UserEntity newUser = map(userRegistration);

        // 2. Load the default role (e.g., USER) from your userRoleRepository
        UserRoleEntity defaultUserRole = userRoleRepository
                .findByRole(UserRoleEnum.USER)
                .orElseThrow(() -> new IllegalArgumentException("Could not find default USER role"));

        // 3. Assign the default role to the new user
        newUser.getRoles().add(defaultUserRole);

        // 4. Save the user
        userRepository.save(newUser);

    }

    @Override
    public Optional<SmartFoodBoxUserDetails> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null &&
                authentication.getPrincipal() instanceof SmartFoodBoxUserDetails smartFoodBoxUserDetails) {
            return Optional.of(smartFoodBoxUserDetails);
        }
        return Optional.empty();
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void assignRoleToUser(Long userId, UserRoleEnum role) {
        // 1. Ensure we're only dealing with USER or ADMIN
        if (role != UserRoleEnum.USER && role != UserRoleEnum.ADMIN) {
            throw new IllegalArgumentException("Role must be USER or ADMIN only.");
        }
        // 2. Load the user
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + userId));

        // 3. Fetch the role entity (must return Optional<UserRoleEntity>)
        UserRoleEntity roleEntity = userRoleRepository.findByRole(role)
                .orElseThrow(() -> new IllegalArgumentException("No role found for: " + role));

        // 4. Add the role if not already present
        if (!userEntity.getRoles().contains(roleEntity)) {
            userEntity.getRoles().add(roleEntity);
            userRepository.save(userEntity);
        }

    }

    @Override
    public Optional<ProfileDTO> getCurrentUserProfile() {
        return getCurrentUser().map(currentUser -> {
            UserEntity userEntity = userRepository.findById(currentUser.getId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + currentUser.getId()));

            ProfileDTO profileDTO = modelMapper.map(userEntity, ProfileDTO.class);
            return profileDTO;
        });
    }

    @Override
    public void updateCurrentUserProfile(ProfileDTO profileDTO) {
        getCurrentUser().ifPresent(currentUser -> {
            UserEntity userEntity = userRepository.findById(currentUser.getId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + currentUser.getId()));

            // Update basic fields
            userEntity.setFirstName(profileDTO.getFirstName());
            userEntity.setLastName(profileDTO.getLastName());

            // If user is allowed to change email (not read-only):
            // userEntity.setEmail(profileDTO.getEmail());

            if (profileDTO.getNewPassword() != null && !profileDTO.getNewPassword().isBlank()) {

                // Optional: check oldPassword to confirm identity
                if (!passwordEncoder.matches(profileDTO.getOldPassword(), userEntity.getPassword())) {
                    throw new IllegalArgumentException("{profile.oldPassword.mismatch}");
                }

                // Check that newPassword = confirmNewPassword manually or via custom validator
                if (!profileDTO.getNewPassword().equals(profileDTO.getConfirmNewPassword())) {
                    throw new IllegalArgumentException("{profile.passwords.notMatching}");
                }

                // Encrypt and set the new password
                userEntity.setPassword(passwordEncoder.encode(profileDTO.getNewPassword()));
            }

            userRepository.save(userEntity);
        });


    }

    private UserEntity map(UserRegistrationDTO userRegistrationDTO) {
        UserEntity mappedEntity = modelMapper.map(userRegistrationDTO, UserEntity.class);

        mappedEntity.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));

        return mappedEntity;
    }
}
