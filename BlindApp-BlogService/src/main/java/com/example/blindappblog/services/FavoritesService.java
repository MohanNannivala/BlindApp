package com.example.blindappblog.services;


import com.example.blindappblog.dtos.ArticlesDto;
import com.example.blindappblog.models.Articles;
import com.example.blindappblog.models.Favorites;
import com.example.blindappblog.repositories.FavoritesRepository;
import com.example.blindappblog.repositories.ArticleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class FavoritesService {

    private final FavoritesRepository favoritesRepository;
    private final ModelMapper modelMapper;
    private final ArticleRepository articlesRepository;

    public FavoritesService(FavoritesRepository favoritesRepository,
                            ModelMapper modelMapper,
                            ArticleRepository articlesRepository) {
        this.favoritesRepository = favoritesRepository;
        this.modelMapper = modelMapper;
        this.articlesRepository = articlesRepository;
    }

    public ResponseEntity<List<ArticlesDto>> getFavoriteArticles(UUID userId) {

        List<Favorites> favorites = favoritesRepository.findAllByUserId(userId);
        List<UUID> favoriteIds = new ArrayList<>();

        for(Favorites favorite : favorites) {
            favoriteIds.add(favorite.getArticleId());
        }

        List<Articles> articles = articlesRepository.findAllById(favoriteIds);

        List<ArticlesDto> articlesDto = new ArrayList<>();

        for(Articles article : articles) {
            articlesDto.add(modelMapper.map(article, ArticlesDto.class));
        }

        return new ResponseEntity<>(articlesDto, HttpStatus.OK);
    }

    public ResponseEntity<Void> createFavoriteArticles(UUID userId, UUID articleId) {
        favoritesRepository.save(new Favorites(userId, articleId));
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
