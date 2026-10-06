package com.fittrack.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlcoholLogDto {

    private Long id;

    @NotNull(message = "La date est obligatoire")
    private LocalDateTime date;

    @NotNull(message = "La quantité est obligatoire")
    private Integer quantityUnits;

    private String notes;

    @NotNull(message = "L'ID de l'utilisateur est obligatoire")
    private Long userId;
}