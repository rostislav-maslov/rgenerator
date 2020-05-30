package tech.maslov.rgenerator.adapter.developer.mapper;

import com.rcore.commons.mapper.ExampleDataMapper;
import tech.maslov.rgenerator.adapter.developer.dto.DeveloperDTO;
import tech.maslov.rgenerator.domain.developer.entity.DeveloperEntity;

public class DeveloperMapper implements ExampleDataMapper<DeveloperEntity, DeveloperDTO> {

    @Override
    public DeveloperDTO map(DeveloperEntity entity) {
        return DeveloperDTO.builder()
                .id(entity.getId())
                .githubConnected(entity.getGitHubAccessToken() != null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .timeZone(entity.getTimeZone())
                .build();
    }

    @Override
    public DeveloperEntity inverseMap(DeveloperDTO dto) {
        return DeveloperEntity.builder()
                .id(dto.getId())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .timeZone(dto.getTimeZone())
                .build();
    }
}