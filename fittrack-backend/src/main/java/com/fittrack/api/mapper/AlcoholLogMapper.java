package com.fittrack.api.mapper;

import org.springframework.stereotype.Component;

import com.fittrack.api.dto.AlcoholLogDto;
import com.fittrack.api.model.AlcoholLog;
import com.fittrack.api.model.User;

@Component
public class AlcoholLogMapper {
    
    public AlcoholLogDto toDto(AlcoholLog entity) {
        if(entity == null) return null;

        return AlcoholLogDto.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .quantityUnits(entity.getQuantityUnits())
                .notes(entity.getNotes())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .build();
    }

    public AlcoholLog toEntity(AlcoholLogDto dto, User user) {
        if(dto == null) return null;

        return AlcoholLog.builder()
                .id(dto.getId())
                .date(dto.getDate())
                .quantityUnits(dto.getQuantityUnits())
                .notes(dto.getNotes())
                .user(user)
                .build();
    }
}
