package com.example.blindappblog.controllers;


import com.example.blindappblog.dtos.ArticlesDto;
import com.example.blindappblog.dtos.CreateArticleDto;
import com.example.blindappblog.services.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ArticlesController {

    private final ArticleService articleService;

    public ArticlesController(ArticleService articleService) {
        this.articleService = articleService;
    }


    @GetMapping("/articles")
    public ResponseEntity<List<ArticlesDto>> getAllArticles() {
        return articleService.getAllArticles();
    }

    @PostMapping("/articles")
    public ResponseEntity<Void> createArticles(@RequestBody CreateArticleDto article) {
        return articleService.createArticles(article);
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<List<ArticlesDto>>  getArticleByUserId(@PathVariable String id) {
        return articleService.getArticleByUserId(UUID.fromString(id));
    }

    @GetMapping("/articles/favorites")
    public String getFavoriteArticles() {
        return null;
    }

    @GetMapping("/articles/{id}/comments")
    public String getCommentsByArticleId(@RequestParam String id) {
        return null;
    }

    @PostMapping("/articles/favorites")
    public String CreateFavoriteArticles() {
        return null;
    }
}
