package com.smartFoodBox.SmartFoodBox.web;

import com.smartFoodBox.SmartFoodBox.model.dto.UserRegistrationDTO;
import com.smartFoodBox.SmartFoodBox.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }
    @ModelAttribute("registerDTO")
    public UserRegistrationDTO registerDTO() {
        return new UserRegistrationDTO();
    }

    @GetMapping("/register")
    public String showRegistrationPage() {
        return "users/register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegistrationDTO registerDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            registerDTO.setPassword(null);
            return "users/register";
        }

        if (userService.emailExists(registerDTO.getEmail())) {
            model.addAttribute("emailExistsError", true);
            registerDTO.setPassword(null);
            return "users/register";
        }
        userService.registerUser(registerDTO);

        return "redirect:/users/login";
    }
}
