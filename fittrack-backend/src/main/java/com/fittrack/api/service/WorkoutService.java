package com.fittrack.api.service;

import com.fittrack.api.dto.WorkoutDto;
import com.fittrack.api.mapper.WorkoutMapper;
import com.fittrack.api.model.ActivityType;
import com.fittrack.api.model.User;
import com.fittrack.api.model.Workout;
import com.fittrack.api.repository.ActivityTypeRepository;
import com.fittrack.api.repository.UserRepository;
import com.fittrack.api.repository.WorkoutRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;
    private final ActivityTypeRepository activityTypeRepository;
    private final WorkoutMapper workoutMapper;

    @Transactional(readOnly = true)
    public List<WorkoutDto> getWorkoutsByUserId(Long userId) {
        // Option A : vérification stricte de l'existence de l'utilisateur
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + userId);
        }

        return workoutRepository.findByUserIdOrderByDateDesc(userId)
                .stream()
                .map(workoutMapper::toDto)
                .toList();
    }

    @Transactional
    public WorkoutDto createWorkout(WorkoutDto dto) {
        // 1. Récupérer l'utilisateur obligatoire
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + dto.getUserId()));

        // 2. Récupérer le type d'activité (si présent dans le DTO)
        ActivityType activityType = null;
        if (dto.getActivityTypeId() != null) {
            activityType = activityTypeRepository.findById(dto.getActivityTypeId())
                    .orElseThrow(() -> new EntityNotFoundException("Type d'activité non trouvé avec l'ID : " + dto.getActivityTypeId()));
        }

        // 3. Mapping DTO -> Entity avec les références d'entités
        Workout workout = workoutMapper.toEntity(dto, user, activityType);

        // 4. Sauvegarde
        Workout savedWorkout = workoutRepository.save(workout);

        // 5. Retour en DTO
        return workoutMapper.toDto(savedWorkout);
    }

    @Transactional
    public void deleteWorkout(Long id) {
        if (!workoutRepository.existsById(id)) {
            throw new EntityNotFoundException("Workout non trouvé avec l'ID : " + id);
        }
        workoutRepository.deleteById(id);
    }
}