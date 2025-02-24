package com.smartFoodBox.SmartFoodBox.web;

import com.smartFoodBox.SmartFoodBox.model.dto.ProfileDTO;
import com.smartFoodBox.SmartFoodBox.service.UserService;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users/profile")
public class ProfileController {
    private final UserService userService;
    private final MessageSource messageSource;

    public ProfileController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    //Initializes an empty ProfileDTO when the controller is created
    @ModelAttribute("profileDTO")
    public ProfileDTO initProfileDTO() {
        return new ProfileDTO();
    }

    @GetMapping
    public String showProfile(Model model) {
        ProfileDTO profileDTO = userService.getCurrentUserProfile()
                .orElseThrow(() -> new IllegalStateException("No logged-in user."));
        model.addAttribute("profileDTO", profileDTO);
        return "users/profile"; // points to profile.html in templates/users/
    }

    @PostMapping
    public String updateProfile(@Valid @ModelAttribute("profileDTO") ProfileDTO profileDTO,
                                BindingResult bindingResult,
                                Model model) {

        // If basic validation fails, go back to form
        if (bindingResult.hasErrors()) {
            return "users/profile";
        }

        try {
            userService.updateCurrentUserProfile(profileDTO);
        } catch (IllegalArgumentException e) {
            // The exception might be a property key like "profile.oldPassword.mismatch" or a raw message
            String localizedError = e.getMessage();
            // If it matches one of your property keys, we fetch the localized text
            if (localizedError.startsWith("profile.")) {
                localizedError = messageSource.getMessage(e.getMessage(), null, LocaleContextHolder.getLocale());
            }
            model.addAttribute("errorMessage", localizedError);
            return "users/profile";
        }

        // Optionally add a success message
        model.addAttribute("successMessage", "Profile updated successfully!");
        return "redirect:/users/profile";
    }
}
