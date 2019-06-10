package com.jotov.myaskme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="User exists!")
public class UserExistsException extends RuntimeException {
}
