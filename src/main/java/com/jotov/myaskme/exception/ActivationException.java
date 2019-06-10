package com.jotov.myaskme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="Activation code is not found!")
public class ActivationException extends RuntimeException{
}
