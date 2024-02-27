package com.example.userservice.services;

import com.example.userservice.dtos.UserDto;
import com.example.userservice.exceptions.UserNotFoundException;
import com.example.userservice.models.Users;
import com.example.userservice.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.UUID;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<UserDto> getUserDetails(@PathVariable("id") String id) throws UserNotFoundException {

        Optional<Users> user = userRepository.findById(UUID.fromString(id));

        if(user.isEmpty()){
            throw new UserNotFoundException("User not found");
        }

        return user.map(users -> new ResponseEntity<>(modelMapper.map(users, UserDto.class), HttpStatus.OK)).orElse(null);

    }
}
