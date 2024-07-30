package com.smartFoodBox.SmartFoodBox.repository;

import com.smartFoodBox.SmartFoodBox.model.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
}
