package com.jotov.myaskme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="Passwords are different or one of them is empty!")
public class PasswordException extends RuntimeException {
}
