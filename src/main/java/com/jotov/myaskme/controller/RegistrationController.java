package com.jotov.myaskme.controller;

import com.jotov.myaskme.domain.User;
import com.jotov.myaskme.domain.dto.CaptchaResponseDto;
import com.jotov.myaskme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    private static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Autowired
    private UserService userService;
    @Value("${recaptcha.secret}")
    private String secret;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/registration")
    public String registration() {
        return"registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("password2") String passwordConfirmation,
            @RequestParam("g-recaptcha-response") String catchaResponse,
            @Valid User user,
            BindingResult bindingResult,
            Model model) {

        String url = String.format(CAPTCHA_URL, secret,catchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirmation);
        if(isConfirmEmpty){
            model.addAttribute("password2Error", "Password confirmation cannot be empty");
        }
        if( user.getPassword() != null && !user.getPassword().equals(passwordConfirmation)){
            model.addAttribute("passwordError", "Passwords are different!");
        }
        if(isConfirmEmpty || bindingResult.hasErrors() ){//|| !response.isSuccess()){
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "registration";
        }
        if(!userService.addUser (user)) {
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }

        return "redirect:/login";
    }
}
