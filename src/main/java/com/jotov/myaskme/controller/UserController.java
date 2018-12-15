package com.jotov.myaskme.controller;

import com.jotov.myaskme.domain.Role;
import com.jotov.myaskme.domain.User;
import com.jotov.myaskme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username",user.getUsername());
        model.addAttribute("email", user.getEmail());

        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(
            Model model,
            @AuthenticationPrincipal User user,
            @RequestParam("current_password") String currentPassword,
            @RequestParam("password") String password,
            @RequestParam("password2") String passwordConfirmation,
            @RequestParam String email
    ) {
        model.addAttribute("username",user.getUsername());
        model.addAttribute("email", user.getEmail());

        if(! StringUtils.isEmpty(currentPassword )) {
            String encOldPass = passwordEncoder.encode(currentPassword);
            boolean isCorrectCurrPass = encOldPass.equals(user.getPassword());
            if(! isCorrectCurrPass ) {
                model.addAttribute("currentPassword", "Current Password is not correct");
                return "profile";
            }
            //check
            boolean isPasswordEmpty = StringUtils.isEmpty(password);
            boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirmation);
            if(isPasswordEmpty) {
                model.addAttribute("passwordError", "Password cannot be empty");
                return "profile";
            } else if(isConfirmEmpty){
                model.addAttribute("password2Error", "Password confirmation cannot be empty");
                return "profile";
            } else if(password != null && password.equals(passwordConfirmation)) {
                model.addAttribute("password2Error", "Password confirmation cannot be empty");
                return "profile";
            }
        }

        userService.updateProfile(user, password, email);

        return "redirect:/user/profile";
    }
}
