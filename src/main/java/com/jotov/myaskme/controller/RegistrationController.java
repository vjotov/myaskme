package com.jotov.myaskme.controller;

import com.jotov.myaskme.dto.CaptchaResponseDto;
import com.jotov.myaskme.dto.CreateUserDto;
import com.jotov.myaskme.exception.ActivationException;
import com.jotov.myaskme.exception.CaptchaException;
import com.jotov.myaskme.exception.PasswordException;
import com.jotov.myaskme.exception.UserExistsException;
import com.jotov.myaskme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;

@RestController
public class RegistrationController {
    private static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Autowired
    private UserService userService;
    @Value("${recaptcha.secret}")
    private String secret;

    @Autowired
    private RestTemplate restTemplate;



    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.OK)
    public String addUser(
            /*@Valid*/ @RequestBody CreateUserDto userDto
    ) {

        boolean isConfirmEmpty = StringUtils.isEmpty(userDto);
        if(isConfirmEmpty || ( userDto.getPassword() != null && !userDto.getPassword().equals(userDto.getPasswordConfirmation()))){
            throw new PasswordException();
        }
        if(!userService.addUser (userDto)) {
            throw new UserExistsException();
        }

        return "User has been successfully created. Please check your e-mail for activation code.";
    }

    @GetMapping("/activate/{code}")
    @ResponseStatus(HttpStatus.OK)
    public String activate(@PathVariable String code){
        boolean isActivated = userService.activateUser(code);
        if(isActivated) {
            return "{message: User successfully activated.}";

        } else {
            Exception ex = new ActivationException();
        }

        return "login";
    }
}
