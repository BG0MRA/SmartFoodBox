package com.smartFoodBox.SmartFoodBox.repository;

import com.smartFoodBox.SmartFoodBox.model.entity.UserRoleEntity;
import com.smartFoodBox.SmartFoodBox.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
    UserRoleEntity findByRole(UserRoleEnum userRoleEnum);
}
