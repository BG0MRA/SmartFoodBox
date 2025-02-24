package com.smartFoodBox.SmartFoodBox.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ProfileDTO {
    @NotEmpty(message = "{profile.firstName.required}")
    @Size(min = 5, max = 20, message = "{profile.firstName.size}")
    private String firstName;

    @NotEmpty(message = "{profile.lastName.required}")
    @Size(min = 5, max = 20, message = "{profile.lastName.size}")
    private String lastName;

    // Email is often read-only if you do not allow changing it
    @NotEmpty(message = "{profile.email.required}")
    @Email(message = "{profile.email.invalid}")
    private String email;

    @NotEmpty(message = "{profile.password.required}")
    @Size(min = 8, message = "{profile.password.size}")
    // Optional fields for password update
    private String oldPassword;

    @NotEmpty(message = "{profile.password.required}")
    @Size(min = 8, message = "{profile.password.size}")
    private String newPassword;

    @NotEmpty(message = "{profile.password.required}")
    @Size(min = 8, message = "{profile.password.size}")
    private String confirmNewPassword;

    // Getters/Setters

    public String getFirstName() {
        return firstName;
    }

    public ProfileDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public ProfileDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ProfileDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public ProfileDTO setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public ProfileDTO setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public ProfileDTO setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
        return this;
    }
}
