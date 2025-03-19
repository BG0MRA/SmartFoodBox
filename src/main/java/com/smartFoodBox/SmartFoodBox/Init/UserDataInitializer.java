package com.smartFoodBox.SmartFoodBox.Init;

import com.smartFoodBox.SmartFoodBox.model.entity.UserEntity;
import com.smartFoodBox.SmartFoodBox.model.entity.UserRoleEntity;
import com.smartFoodBox.SmartFoodBox.model.enums.UserRoleEnum;
import com.smartFoodBox.SmartFoodBox.repository.UserRepository;
import com.smartFoodBox.SmartFoodBox.repository.UserRoleRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class UserDataInitializer {
    @Bean
    ApplicationRunner init(UserRepository userRepository, UserRoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (roleRepository.count() == 0) {
                UserRoleEntity adminRole = new UserRoleEntity();
                adminRole.setRole(UserRoleEnum.ADMIN);
                roleRepository.save(adminRole);

                UserRoleEntity userRole = new UserRoleEntity();
                userRole.setRole(UserRoleEnum.USER);
                roleRepository.save(userRole);
            }

            if (userRepository.count() == 0) {
                UserRoleEntity adminRole = roleRepository.findByRole(UserRoleEnum.ADMIN).orElseThrow(() -> new IllegalStateException("Admin role not found"));
                UserRoleEntity userRole = roleRepository.findByRole(UserRoleEnum.USER).orElseThrow(() -> new IllegalStateException("User role not found"));

                UserEntity admin = new UserEntity();
                admin.setEmail("admin@example.com")
                        .setFirstName("Admin")
                        .setLastName("Admin")
                        .setPassword(passwordEncoder.encode("admin1234"))
                        .setRoles(Set.of(adminRole));
                userRepository.save(admin);

                UserEntity user = new UserEntity();
                user.setEmail("user@example.com")
                        .setFirstName("Normal")
                        .setLastName("Normal")
                        .setPassword(passwordEncoder.encode("user1234"))
                        .setRoles(Set.of(userRole));
                userRepository.save(user);
            }
        };
    }
}