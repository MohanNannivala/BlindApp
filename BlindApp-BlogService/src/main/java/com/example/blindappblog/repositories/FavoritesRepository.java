package com.example.blindappblog.repositories;

import com.example.blindappblog.models.Articles;
import com.example.blindappblog.models.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorites, UUID> {

    List<Favorites> findAllByUserId(UUID userId);

    Favorites save(Favorites entity);
}
