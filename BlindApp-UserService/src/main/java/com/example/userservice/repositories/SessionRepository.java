package com.example.userservice.repositories;

import com.example.userservice.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {

    @Query(nativeQuery = true, value = "SELECT s.* FROM Session s, Users u WHERE s.token = ?1 AND s.user_id = ?2")
    List<Session> findAllSessions(String token, UUID userId);
}
