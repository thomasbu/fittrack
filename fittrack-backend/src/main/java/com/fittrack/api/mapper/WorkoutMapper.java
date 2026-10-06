package com.fittrack.api.mapper;

import com.fittrack.api.dto.WorkoutDto;
import com.fittrack.api.model.ActivityType;
import com.fittrack.api.model.User;
import com.fittrack.api.model.Workout;
import org.springframework.stereotype.Component;

@Component
public class WorkoutMapper {

    public WorkoutDto toDto(Workout entity) {
        if (entity == null) {
            return null;
        }

        return WorkoutDto.builder()
                .id(entity.getId())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .activityTypeId(entity.getActivityType() != null ? entity.getActivityType().getId() : null)
                .date(entity.getDate())
                .durationMinutes(entity.getDurationMinutes())
                .distanceKm(entity.getDistanceKm())
                .calories(entity.getCalories())
                .notes(entity.getNotes())
                .build();
    }

    public Workout toEntity(WorkoutDto dto, User user, ActivityType activityType) {
        if (dto == null) {
            return null;
        }

        return Workout.builder()
                .id(dto.getId())
                .user(user)
                .activityType(activityType)
                .date(dto.getDate())
                .durationMinutes(dto.getDurationMinutes())
                .distanceKm(dto.getDistanceKm())
                .calories(dto.getCalories())
                .notes(dto.getNotes())
                .build();
    }
}