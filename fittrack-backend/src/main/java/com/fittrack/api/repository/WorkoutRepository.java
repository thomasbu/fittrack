package com.fittrack.api.repository;

import com.fittrack.api.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    // Récupère les entraînements d'un utilisateur triés par date décroissante
    List<Workout> findByUserIdOrderByDateDesc(Long userId);
}