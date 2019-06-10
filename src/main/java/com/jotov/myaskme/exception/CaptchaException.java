package com.jotov.myaskme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Your request doesn't contain CATCHA code!")
public class CaptchaException extends RuntimeException {
}
