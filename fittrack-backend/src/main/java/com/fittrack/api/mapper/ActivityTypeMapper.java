package com.fittrack.api.mapper;

import com.fittrack.api.dto.ActivityTypeDto;
import com.fittrack.api.model.ActivityType;
import org.springframework.stereotype.Component;

@Component
public class ActivityTypeMapper {

    public ActivityTypeDto toDto(ActivityType entity) {
        if (entity == null) return null;

        return ActivityTypeDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .category(entity.getCategory())
                .build();
    }

    public ActivityType toEntity(ActivityTypeDto dto) {
        if (dto == null) return null;

        return ActivityType.builder()
                .id(dto.getId())
                .name(dto.getName())
                .category(dto.getCategory())
                .build();
    }
}