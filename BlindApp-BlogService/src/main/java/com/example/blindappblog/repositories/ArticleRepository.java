package com.example.blindappblog.repositories;

import com.example.blindappblog.models.Articles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArticleRepository extends JpaRepository<Articles, UUID>{
    List<Articles> findAllByUserId(UUID userId);

    @Override
    List<Articles> findAllById(Iterable<UUID> uuids);
}
