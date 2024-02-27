package com.example.blindappblog.repositories;

import com.example.blindappblog.models.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface LikeRepository extends JpaRepository<Likes, UUID>{
}
