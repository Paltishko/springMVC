package com.RestTutorial.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by atretjak on 25.12.2016.
 */

class UserNotFoundException extends RuntimeException {
    UserNotFoundException(String userId) {
        super("could not find user '" + userId + "'.");
    }
}
