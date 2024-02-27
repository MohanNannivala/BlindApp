package com.example.blindappblog.services;

import com.example.blindappblog.dtos.ArticlesDto;
import com.example.blindappblog.dtos.CreateArticleDto;
import com.example.blindappblog.models.Articles;
import com.example.blindappblog.repositories.ArticleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public ResponseEntity<List<ArticlesDto>> getAllArticles() {
        List<Articles> articles = articleRepository.findAll();
        List<ArticlesDto> response = new ArrayList<>();



        for(Articles article : articles){
           ArticlesDto articlesDto = new ArticlesDto();
           articlesDto.setArticleId(article.getUuid());
           articlesDto.setTitle(article.getTitle());
           articlesDto.setContent(article.getContent().substring(0,Integer.min(article.getContent().length(), 100))+"...");
           articlesDto.setNumberOfComments(article.getComments().size());
           articlesDto.setNumberOfLikes(Integer.valueOf("0"));
           response.add(articlesDto);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Void> createArticles(CreateArticleDto article) {
        articleRepository.save(new Articles(article.getTitle(), article.getContent(), article.getUserId()));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<ArticlesDto> getArticleById(UUID id) {
        Optional<Articles> article =articleRepository.findById(id);

        ArticlesDto articlesDto = new ArticlesDto();

        if(article.isPresent()){
            articlesDto.setTitle(article.get().getTitle());
            articlesDto.setContent(article.get().getContent().substring(0,20)+"...");
            articlesDto.setNumberOfComments(article.get().getComments().size());
            articlesDto.setNumberOfLikes(Integer.valueOf("0"));
        }

        return new ResponseEntity<>(articlesDto, HttpStatus.OK);
    }

    public String getCommentsByArticleId(@RequestParam UUID id) {
        return null;
    }

    public String getFavoriteArticles() {
        return null;
    }

    public String CreateFavoriteArticles() {
        return null;
    }

    public ResponseEntity<List<ArticlesDto>> getArticleByUserId(UUID userId) {

        List<Articles> articles = articleRepository.findAllByUserId(userId);

        List<ArticlesDto> response = new ArrayList<>();



        for(Articles article : articles){
            ArticlesDto articlesDto = new ArticlesDto();
            articlesDto.setArticleId(article.getUuid());
            articlesDto.setTitle(article.getTitle());
            articlesDto.setContent(article.getContent().substring(0,Integer.min(article.getContent().length(), 100))+"...");
            articlesDto.setNumberOfComments(article.getComments().size());
            articlesDto.setNumberOfLikes(Integer.valueOf("0"));
            response.add(articlesDto);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
