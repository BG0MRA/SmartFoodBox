package com.smartFoodBox.SmartFoodBox.model.entity;

import com.smartFoodBox.SmartFoodBox.model.enums.UserRoleEnum;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name = "roles")
public class UserRoleEntity extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;


    public UserRoleEnum getRole() {
        return role;
    }

    public UserRoleEntity setRole(UserRoleEnum role) {
        this.role = role;
        return this;
    }

    @Override
    public String toString() {
        return "UserRoleEntity{" +
                "role=" + role +
                '}';
    }
}
