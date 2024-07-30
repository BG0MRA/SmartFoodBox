package com.smartFoodBox.SmartFoodBox.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(
                    authorizeRequests -> authorizeRequests
                            // all static resources to "common locations" (css, images, js) are available to anyone
                            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                            // some more resources for all users (login, register page etc.)
                            .requestMatchers("/").permitAll()
                            // all other URL-s should be authenticated.
                            .anyRequest()
                            .authenticated()
                )
                .formLogin(
                    formLogin -> formLogin
                            // Where is our custom login form?
                            .loginPage("/login")
                            // what is the name of the username parameter in the Login POST request?
                            .usernameParameter("email")
                            // what is the name of the password parameter in the Login POST request?
                            .passwordParameter("password")
                            // What will happen if the login is successful
                            .defaultSuccessUrl("/", true)
                            // What will happen if the login fails
                            .failureForwardUrl("/users/login-error")
                )
                .logout(
                        logout ->
                                logout
                                        // what is the logout URL?
                                        .logoutUrl("/users/logout")
                                        // Where to go after successful logout?
                                        .logoutSuccessUrl("/")
                                        // invalidate the session after logout.
                                        .invalidateHttpSession(true)
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
