package com.example.userservice.services;

import com.example.userservice.dtos.LogoutRequestDto;
import com.example.userservice.dtos.UserDto;
import com.example.userservice.exceptions.UserAlreadyExistsException;
import com.example.userservice.exceptions.UserNotFoundException;
import com.example.userservice.exceptions.WrongPasswordException;
import com.example.userservice.models.Session;
import com.example.userservice.models.SessionStatus;
import com.example.userservice.models.Users;
import com.example.userservice.repositories.SessionRepository;
import com.example.userservice.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;
    private final SessionRepository sessionRepository;
    private final JwtService JwtService;

    public AuthService(UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       ModelMapper modelMapper,
                       SessionRepository sessionRepository,
                        JwtService jwtService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
        this.sessionRepository = sessionRepository;
        JwtService = jwtService;
    }

    public ResponseEntity<UserDto> login(String email, String password) throws UserNotFoundException, WrongPasswordException {

        Users user = userRepository.findByUsername(email);

        if(user == null){
            throw new UserNotFoundException("User not found");
        }

        if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
            throw new WrongPasswordException("Wrong password");
        }

        String token = JwtService.generateToken(user);

        Session session = new Session();
        session.setToken(token);
        session.setUser(user);
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setExpiresAt(new Date(Long.parseLong(String.valueOf(JwtService.verifyToken(token).get("expiryAt")))));
        sessionRepository.save(session);

        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, token);

        ResponseEntity<UserDto> responseEntity = new ResponseEntity<>(modelMapper.map(user, UserDto.class), headers, HttpStatus.OK);
        responseEntity.getBody().setUserId(user.getUuid());
        return responseEntity;
    }

    public ResponseEntity<Void> logout(LogoutRequestDto requestDto){

        List<Session> session = sessionRepository.findAllSessions(requestDto.getToken(), requestDto.getUserId());


        for (Session s : session) {
            if (s.getSessionStatus().equals(SessionStatus.ACTIVE)) {
                s.setSessionStatus(SessionStatus.ENDED);
                sessionRepository.save(s);
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<UserDto> signup(String userName, String password) throws UserAlreadyExistsException {

        if(userRepository.findByUsername(userName) != null){
            throw new UserAlreadyExistsException("User already exists");
        }

        Users users = new Users();
        users.setUsername(userName);
        users.setPassword(bCryptPasswordEncoder.encode(password));

        Users savedUser = userRepository.save(users);

        return new ResponseEntity<>(modelMapper.map(savedUser, UserDto.class), HttpStatus.OK);
    }

    public SessionStatus validate(String token, UUID userId) {

        List<Session> session = sessionRepository.findAllSessions(token, userId);


//        if(session == null || session.isEmpty()){
//            throw new UserNotAuthorizedException("User not authorized");
//        }

        if(session == null || session.isEmpty()){
            return SessionStatus.NOT_FOUND;
        }


        for(Session s: session){
            if(s.getSessionStatus().equals(SessionStatus.ACTIVE)){
                return SessionStatus.ACTIVE;
            }
        }

        return SessionStatus.ENDED;

    }
}
