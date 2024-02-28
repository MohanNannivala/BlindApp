package com.example.blindappui.controllers;


import com.example.blindappui.Dtos.ArticlesDto;
import com.example.blindappui.Dtos.CreateArticleDto;
import com.example.blindappui.Dtos.SessionStatus;
import com.example.blindappui.Dtos.ValidateTokenRequestDto;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping
public class HomePageController {

    private final RestTemplateBuilder restTemplateBuilder;

    @Value("${blog.service.url}")
    private String blogServiceURL;

    @Value("${user.service.url}")
    private String userServiceURL;

    @Value("${ui.service.url}")
    private String uiServiceURL;

    public HomePageController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    private ResponseEntity<SessionStatus> validateToken(String token, UUID userId) {
        ValidateTokenRequestDto requestDto = new ValidateTokenRequestDto(token, userId);
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<SessionStatus> response = restTemplate.exchange(userServiceURL+"/auth/validate", HttpMethod.POST, new HttpEntity<>(requestDto), SessionStatus.class);
        return response;
    }

    @GetMapping("/homepage")
    public String homepage(@Nullable @CookieValue("auth-token") String token,
                           @Nullable @CookieValue("userId") String userId,
                            Model model){

        if (!validateToken(token, UUID.fromString(userId)).getBody().equals(SessionStatus.ACTIVE)) {
            return "exception";
        }

        RestTemplate restTemplate = restTemplateBuilder.build();
        ValidateTokenRequestDto requestDto = new ValidateTokenRequestDto(token, UUID.fromString(userId));
        ResponseEntity<ArticlesDto[]> response = restTemplate.exchange(blogServiceURL+"/articles", HttpMethod.GET, new HttpEntity<>(requestDto), ArticlesDto[].class);

        model.addAttribute("userId", userId);
        model.addAttribute("uiServiceURL", uiServiceURL);
        model.addAttribute("articles", List.of(response.getBody()));

        return "homepage";
    }


    @GetMapping("/createpost")
    public String createPost(@Nullable @CookieValue("auth-token") String token,
                             @Nullable @CookieValue("userId") String userId,
                             Model model){
        if (!validateToken(token, UUID.fromString(userId)).getBody().equals(SessionStatus.ACTIVE)) {
            return "exception";
        }

        model.addAttribute("uiServiceURL", uiServiceURL);

        return "createpost";
    }

    @PostMapping("/createpost")
    public String createPost(@Nullable @CookieValue("auth-token") String token,
                             @Nullable @CookieValue("userId") String userId,
                             @ModelAttribute CreateArticleDto createArticleDto,
                             HttpServletResponse httpServletResponse,
                             Model model){

        if (!validateToken(token, UUID.fromString(userId)).getBody().equals(SessionStatus.ACTIVE)) {
            return "exception";
        }

        model.addAttribute("uiServiceURL", uiServiceURL);

        createArticleDto.setUserId(UUID.fromString(userId));
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Void> response = restTemplate.exchange(blogServiceURL+"/articles", HttpMethod.POST, new HttpEntity<>(createArticleDto), Void.class);

        httpServletResponse.addHeader("HX-Redirect", "/homepage");
        return "homepage";
    }

    @GetMapping("/about")
    public String about(){
        return "landingpage";
    }

    @GetMapping("/myPosts")
    public String getMyPosts(@Nullable @CookieValue("auth-token") String token,
                             @Nullable @CookieValue("userId") String userId,
                             @ModelAttribute CreateArticleDto createArticleDto,
                             Model model){

        if (!validateToken(token, UUID.fromString(userId)).getBody().equals(SessionStatus.ACTIVE)) {
            return "exception";
        }

        RestTemplate restTemplate = restTemplateBuilder.build();
        ValidateTokenRequestDto requestDto = new ValidateTokenRequestDto(token, UUID.fromString(userId));
        ResponseEntity<ArticlesDto[]> response = restTemplate.exchange(blogServiceURL+"/articles/"+userId, HttpMethod.GET, new HttpEntity<>(requestDto), ArticlesDto[].class);

        model.addAttribute("userId", userId);
        model.addAttribute("uiServiceURL", uiServiceURL);
        model.addAttribute("articles", List.of(response.getBody()));

        return "mypost";

    }

    @GetMapping("/favorites")
    public String getMyFavorites(@Nullable @CookieValue("auth-token") String token,
                             @Nullable @CookieValue("userId") String userId,
                             @ModelAttribute CreateArticleDto createArticleDto,
                             Model model){

        if (!validateToken(token, UUID.fromString(userId)).getBody().equals(SessionStatus.ACTIVE)) {
            return "exception";
        }

        RestTemplate restTemplate = restTemplateBuilder.build();
        ValidateTokenRequestDto requestDto = new ValidateTokenRequestDto(token, UUID.fromString(userId));
        ResponseEntity<ArticlesDto[]> response = restTemplate.exchange(blogServiceURL+"/favorites?userId="+userId, HttpMethod.GET, new HttpEntity<>(requestDto), ArticlesDto[].class);

        model.addAttribute("userId", userId);
        model.addAttribute("uiServiceURL", uiServiceURL);
        model.addAttribute("articles", List.of(response.getBody()));

        return "favorite";

    }

    @PostMapping("/favorites")
    public void addToFavorites(@Nullable @CookieValue("auth-token") String token,
                             @Nullable @CookieValue("userId") String userId,
                            @RequestParam(name = "articleId") String articleId){

        ValidateTokenRequestDto requestDto = new ValidateTokenRequestDto(token, UUID.fromString(userId));
        RestTemplate restTemplate = restTemplateBuilder.build();

        ResponseEntity<Void> response = restTemplate.exchange(blogServiceURL+"/favorites?userId="+userId+"&articleId="+articleId, HttpMethod.POST, new HttpEntity<>(requestDto), Void.class);

    }

}
