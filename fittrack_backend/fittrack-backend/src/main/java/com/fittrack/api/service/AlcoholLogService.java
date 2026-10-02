package com.fittrack.api.service;

import com.fittrack.api.dto.AlcoholLogDto;
import com.fittrack.api.mapper.AlcoholLogMapper;
import com.fittrack.api.model.AlcoholLog;
import com.fittrack.api.model.User;
import com.fittrack.api.repository.AlcoholLogRepository;
import com.fittrack.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlcoholLogService {

    private final AlcoholLogRepository alcoholLogRepository;
    private final UserRepository userRepository;
    private final AlcoholLogMapper alcoholLogMapper;

    @Transactional(readOnly = true)
    public List<AlcoholLogDto> getLogsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("Utilisateur non trouvé avec l'id : " + userId);
        }

        return alcoholLogRepository.findByUserId(userId).stream()
                .map(alcoholLogMapper::toDto)
                .toList();
    }

    @Transactional
    public AlcoholLogDto createLog(AlcoholLogDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'id : " + dto.getUserId()));

        AlcoholLog entity = alcoholLogMapper.toEntity(dto, user);
        AlcoholLog savedEntity = alcoholLogRepository.save(entity);

        return alcoholLogMapper.toDto(savedEntity);
    }

    @Transactional
    public void deleteLog(Long id) {
        if (!alcoholLogRepository.existsById(id)) {
            throw new RuntimeException("Log d'alcool non trouvé avec l'id : " + id);
        }
        alcoholLogRepository.deleteById(id);
    }
}