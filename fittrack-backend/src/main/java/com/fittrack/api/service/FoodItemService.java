package com.fittrack.api.service;

import com.fittrack.api.dto.FoodItemDto;
import com.fittrack.api.mapper.FoodItemMapper;
import com.fittrack.api.model.FoodItem;
import com.fittrack.api.model.User;
import com.fittrack.api.repository.FoodItemRepository;
import com.fittrack.api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodItemService {

    private final FoodItemRepository foodItemRepository;
    private final UserRepository userRepository;
    private final FoodItemMapper foodItemMapper;

    @Transactional(readOnly = true)
    public FoodItemDto getFoodItemById(Long id) {
        FoodItem foodItem = foodItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aliment non trouvé avec l'ID : " + id));
        return foodItemMapper.toDto(foodItem);
    }

    @Transactional(readOnly = true)
    public List<FoodItemDto> getFoodItemsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + userId);
        }

        return foodItemRepository.findByUserIdOrderByNameAsc(userId)
                .stream()
                .map(foodItemMapper::toDto)
                .toList();
    }

    @Transactional
    public FoodItemDto createFoodItem(FoodItemDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + dto.getUserId()));

        FoodItem foodItem = foodItemMapper.toEntity(dto, user);
        FoodItem savedFoodItem = foodItemRepository.save(foodItem);

        return foodItemMapper.toDto(savedFoodItem);
    }

    @Transactional
    public void deleteFoodItem(Long id) {
        if (!foodItemRepository.existsById(id)) {
            throw new EntityNotFoundException("Aliment non trouvé avec l'ID : " + id);
        }
        foodItemRepository.deleteById(id);
    }
}