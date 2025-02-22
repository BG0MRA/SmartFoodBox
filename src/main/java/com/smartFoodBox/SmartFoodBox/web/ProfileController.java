//package com.smartFoodBox.SmartFoodBox.web;
//
//import com.smartFoodBox.SmartFoodBox.model.user.SmartFoodBoxUserDetails;
//import com.smartFoodBox.SmartFoodBox.service.UserService;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.security.Principal;
//import java.util.Optional;
//
//@Controller
//@RequestMapping("/users/profile")
//public class ProfileController {
//    private final UserService userService;
//
//    public ProfileController(UserService userService) {
//        this.userService = userService;
//    }
//
//    /**
//     * GET - View a user’s profile by ID
//     * Regular users can only view their own profile,
//     * while admins can view any user’s profile.
//     */
//
//    @GetMapping("/{userId}")
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
//    public String showProfilePage(@PathVariable Long userId, Model model, Principal principal) {
//
//        //Getting current user
//        Optional<SmartFoodBoxUserDetails> currentUser = userService.getCurrentUser();
//
//        if (currentUser.)
//
//    }
//
//}
