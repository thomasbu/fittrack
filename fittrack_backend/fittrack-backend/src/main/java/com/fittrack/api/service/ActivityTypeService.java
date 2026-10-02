package com.fittrack.api.service;

import com.fittrack.api.dto.ActivityTypeDto;
import com.fittrack.api.mapper.ActivityTypeMapper;
import com.fittrack.api.model.ActivityType;
import com.fittrack.api.repository.ActivityTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityTypeService {

    private final ActivityTypeRepository activityTypeRepository;
    private final ActivityTypeMapper activityTypeMapper;

    @Transactional(readOnly = true)
    public List<ActivityTypeDto> getAllActivityTypes() {
        return activityTypeRepository.findAll().stream()
                .map(activityTypeMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ActivityTypeDto getActivityTypeById(Long id) {
        ActivityType activityType = activityTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Type d'activité non trouvé avec l'id : " + id));
        return activityTypeMapper.toDto(activityType);
    }

    @Transactional
    public ActivityTypeDto createActivityType(ActivityTypeDto dto) {
        ActivityType activityType = activityTypeMapper.toEntity(dto);
        ActivityType saved = activityTypeRepository.save(activityType);
        return activityTypeMapper.toDto(saved);
    }
}