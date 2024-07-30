package com.smartFoodBox.SmartFoodBox.service.impl;

import com.smartFoodBox.SmartFoodBox.model.entity.UserEntity;
import com.smartFoodBox.SmartFoodBox.model.entity.UserRoleEntity;
import com.smartFoodBox.SmartFoodBox.model.enums.UserRoleEnum;
import com.smartFoodBox.SmartFoodBox.model.user.SmartFoodBoxUserDetails;
import com.smartFoodBox.SmartFoodBox.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static java.util.Arrays.stream;

public class SmartFoodBoxUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public SmartFoodBoxUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        return userRepository
                .findByEmail(email)
                .map(SmartFoodBoxUserDetailsService::map)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found!"));
    }

    private static UserDetails map(UserEntity userEntity) {
        return new SmartFoodBoxUserDetails(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getRoles().stream().map(UserRoleEntity::getRole).map(SmartFoodBoxUserDetailsService::map).toList(),
                userEntity.getFirstName(),
                userEntity.getLastName()
                );
    }

    private static GrantedAuthority map(UserRoleEnum role) {
        return new SimpleGrantedAuthority(
                "ROLE_" + role
        );
    }
}
