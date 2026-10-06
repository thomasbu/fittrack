package com.fittrack.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityTypeDto {
    private Long id;

    @NotBlank(message = "Le nom de l'activité est obligatoire")
    private String name;

    @NotBlank(message = "La catégorie est obligatoire")
    private String category;
}