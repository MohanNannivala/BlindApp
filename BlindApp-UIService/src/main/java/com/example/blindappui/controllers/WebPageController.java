package com.example.blindappui.controllers;

import com.example.blindappui.Dtos.*;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Controller
@RequestMapping()
public class WebPageController {

    private final RestTemplateBuilder restTemplateBuilder;
    private final HomePageController homePageController;

    public WebPageController(RestTemplateBuilder restTemplateBuilder, HomePageController homePageController) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.homePageController = homePageController;
    }

    @GetMapping
    public String index(@Nullable @CookieValue("auth-token") String token,
                        @Nullable @CookieValue("userId") String userId,
                        HttpServletResponse httpServletResponse,
                        Model model

    ) {

        if (token == null || userId == null) {
            return "index";
        }else {
            ValidateTokenRequestDto requestDto = new ValidateTokenRequestDto(token, UUID.fromString(userId));
            RestTemplate restTemplate = restTemplateBuilder.build();
            ResponseEntity<SessionStatus> response = restTemplate.exchange("http://localhost:8080/auth/validate", HttpMethod.POST, new HttpEntity<>(requestDto), SessionStatus.class);

            if (response.getBody().equals(SessionStatus.ACTIVE)) {
                //httpServletResponse.addHeader("HX-Redirect", "/homepage");
                return homePageController.homepage(token, userId, model);
            }
        }

        return "index";
    }


    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/login")
    public String login(HttpServletResponse httpServletResponse) {

        if(httpServletResponse.getHeader("HX-Request") == null){
            return "login";
        }

        return "loginSPA";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequestDto requestDto, HttpServletResponse httpServletResponse, Model model) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<UserDto> response = restTemplate.exchange("http://localhost:8080/auth/login", HttpMethod.POST, new HttpEntity<>(requestDto), UserDto.class);

        //TODO: CORRECT THE BELOW BEFORE GO LIVE, BELOW IS A TEMPORARY SOLUTION TO WORK WITH HTTP
        ResponseCookie resCookieAuthToken = ResponseCookie.from("auth-token", response.getHeaders().getFirst(HttpHeaders.SET_COOKIE))
                .httpOnly(true)
                .sameSite("None")
                .secure(true)
                .path("/")
                .build();

        httpServletResponse.addHeader("Set-Cookie", resCookieAuthToken.toString());

        //TODO: CORRECT THE BELOW BEFORE GO LIVE, BELOW IS A TEMPORARY SOLUTION TO WORK WITH HTTP
        ResponseCookie resCookieUserId = ResponseCookie.from("userId", String.valueOf(response.getBody().getUserId()))
                .httpOnly(true)
                .sameSite("None")
                .secure(true)
                .path("/")
                .build();

        httpServletResponse.addHeader("Set-Cookie", resCookieUserId.toString());
        httpServletResponse.addHeader("HX-Redirect", "/homepage");
        return homePageController.homepage(resCookieAuthToken.getValue(), resCookieUserId.getValue(), model);
    }

    @PostMapping("/logout")
    public String logout(@CookieValue("auth-token") String token,
                         @CookieValue("userId") String userId,
                         HttpServletResponse httpServletResponse){
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Void> response = restTemplate.exchange("http://localhost:8080/auth/logout", HttpMethod.POST, new HttpEntity<>(new LogoutRequestDto(UUID.fromString(userId), token)), Void.class);
        httpServletResponse.addHeader("HX-Redirect", "/");
        return "index";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute SignupRequestDto requestDto, HttpServletResponse httpServletResponse) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<UserDto> response= restTemplate.exchange("http://localhost:8080/auth/signup", HttpMethod.POST, new HttpEntity<>(requestDto), UserDto.class);
        httpServletResponse.addHeader("HX-Redirect", "/");
        return "landingpage";
    }


}
