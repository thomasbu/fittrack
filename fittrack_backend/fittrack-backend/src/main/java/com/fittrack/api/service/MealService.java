package com.fittrack.api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fittrack.api.dto.MealDto;
import com.fittrack.api.mapper.MealMapper;
import com.fittrack.api.model.Meal;
import com.fittrack.api.model.User;
import com.fittrack.api.repository.MealRepository;
import com.fittrack.api.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MealService {

    private final MealRepository mealRepository;
    private final UserRepository userRepository;
    private final MealMapper mealMapper;

    @Transactional(readOnly = true)
    public MealDto getMealById(Long id) {
        Meal meal = mealRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Repas non trouvé avec l'ID : " + id));
        return mealMapper.toDto(meal);
    }

    @Transactional(readOnly = true)
    public List<MealDto> getMealsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + userId);
        }

        return mealRepository.findByUserIdOrderByDateDesc(userId)
                .stream()
                .map(mealMapper::toDto)
                .toList();
    }

    @Transactional
    public MealDto createMeal(MealDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + dto.getUserId()));

        Meal meal = mealMapper.toEntity(dto, user);
        Meal savedMeal = mealRepository.save(meal);

        return mealMapper.toDto(savedMeal);
    }

    @Transactional
    public void deleteMeal(Long id) {
        if (!mealRepository.existsById(id)) {
            throw new EntityNotFoundException("Repas non trouvé avec l'ID : " + id);
        }
        mealRepository.deleteById(id);
    }
}