package com.fittrack.api.repository;

import com.fittrack.api.model.AlcoholLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlcoholLogRepository extends JpaRepository<AlcoholLog, Long> {

    // Spring Data JPA génère automatiquement la requête SQL grâce au nom de la méthode !
    List<AlcoholLog> findByUserId(Long userId);
}