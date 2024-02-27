package com.example.userservice.controllers;

import com.example.userservice.exceptions.UserNotFoundException;
import com.example.userservice.services.UserService;
import com.example.userservice.dtos.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable("id") String id) throws UserNotFoundException {
        return userService.getUserDetails(id);
    }

}
