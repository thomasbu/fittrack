package com.fittrack.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutDto {

    private Long id;

    @NotNull(message = "L'ID utilisateur est obligatoire")
    private Long userId;

    private Long activityTypeId;

    @NotNull(message = "La date est obligatoire")
    private LocalDateTime date;

    @Positive(message = "La durée doit être supérieure à 0")
    private Integer durationMinutes;

    @Positive(message = "La distance doit être supérieure à 0")
    private Integer distanceKm;

    @Positive(message = "Les calories doivent être supérieures à 0")
    private Integer calories;

    private String notes;
}