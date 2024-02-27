package com.example.blindappblog.controllers;


import com.example.blindappblog.dtos.ArticlesDto;
import com.example.blindappblog.services.FavoritesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/favorites")
public class FavoritesController {

    private final FavoritesService favoritesService;

    public FavoritesController(FavoritesService favoritesService) {
        this.favoritesService = favoritesService;
    }


    @GetMapping
    public ResponseEntity<List<ArticlesDto>> getFavoriteArticles(@RequestParam(name = "userId") String userId) {
        return favoritesService.getFavoriteArticles(UUID.fromString(userId));
    }

    @PostMapping
    public ResponseEntity<Void> CreateFavoriteArticles(@RequestParam(name = "userId") String userId,
                                                       @RequestParam(name = "articleId") String articleId) {
        return favoritesService.createFavoriteArticles(UUID.fromString(userId), UUID.fromString(articleId));
    }
}
