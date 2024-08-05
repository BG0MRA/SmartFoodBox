package com.smartFoodBox.SmartFoodBox.model.dto;

import com.smartFoodBox.SmartFoodBox.validation.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@PasswordMatches
public class UserRegistrationDTO {

    @NotEmpty(message = "{register.firstName.required}")
    @Size(min = 5, max = 20, message = "{register.firstName.size}")
    private String firstName;

    @NotEmpty(message = "{register.lastName.required}")
    @Size(min = 5, max = 20, message = "{register.lastName.size}")
    private String lastName;

    @NotEmpty(message = "{register.password.required}")
    @Size(min = 8, message = "{register.password.size}")
    private String password;

    @NotEmpty(message = "{register.confirmPassword.required}")
    @Size(min = 8, message = "{register.password.size}")
    private String confirmPassword;

    @NotEmpty(message = "{register.email.required}")
    @Email(message = "{register.email.invalid}")
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public UserRegistrationDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegistrationDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegistrationDTO setPassword(String password) {
        this.password = password;
        return this;
    }


    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegistrationDTO setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegistrationDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String toString() {
        return "UserRegistrationDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + (password == null ? "N/A" : "[PROVIDED]") + '\'' +
                ", confirmPassword='" + (confirmPassword == null ? "N/A" : "[PROVIDED]") + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
