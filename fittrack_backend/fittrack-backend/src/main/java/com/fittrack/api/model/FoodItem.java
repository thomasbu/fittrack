package com.fittrack.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "food_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(name = "calories_per_100gr", nullable = false)
    private Integer caloriesPer100gr;

    @Column(name = "carbs_per_100gr")
    private Integer carbsPer100gr;

    @Column(name = "fat_per_100gr")
    private Integer fatPer100gr;

    @Column(name = "protein_per_100gr")
    private Integer proteinPer100gr;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}