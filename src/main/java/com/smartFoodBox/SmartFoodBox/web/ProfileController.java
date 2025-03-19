package com.smartFoodBox.SmartFoodBox.web;

import com.smartFoodBox.SmartFoodBox.model.dto.ProfileDTO;
import com.smartFoodBox.SmartFoodBox.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/users/profile")
public class ProfileController {
    private final UserService userService;
    private final HttpServletRequest request;

    public ProfileController(UserService userService,
                             HttpServletRequest request) {
        this.userService = userService;
        this.request = request;
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
            //return to the view error messages
            model.addAttribute("errorMessage", bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", ")));
            return "users/profile";
        }

        try {
            userService.updateCurrentUserProfile(profileDTO);
        } catch (IllegalArgumentException e) {
            // The exception might be a property key like "profile.oldPassword.mismatch" or a raw message
            String localizedError = e.getMessage();
            model.addAttribute("errorMessage", localizedError);
            return "users/profile";
        }

        // Invalidate the current session
        request.getSession().invalidate();

        return "redirect:/users/login?updateSuccess";
    }
}
