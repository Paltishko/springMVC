package com.RestTutorial.controllers;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by atretjak on 25.12.2016.
 */

@ControllerAdvice
public class BookmarkControllerAdvice {
    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors userNotfoundExceptionHandler(UserNotFoundException ex){
        return new VndErrors("error", ex.getMessage());
    }
}
