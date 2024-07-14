package com.smartFoodBox.SmartFoodBox.model.entity;

import com.smartFoodBox.SmartFoodBox.model.enums.UserRoleEnum;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name = "roles")
public class UserRoleEnitity extends BaseEntity {
    @NotNull
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;


    public UserRoleEnum getRole() {
        return role;
    }

    public UserRoleEnitity setRole(UserRoleEnum role) {
        this.role = role;
        return this;
    }
}
