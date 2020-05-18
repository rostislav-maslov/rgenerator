package tech.maslov.rgenerator.adapter.templateResult.mapper;

import com.rcore.commons.mapper.ExampleDataMapper;
import tech.maslov.rgenerator.adapter.templateResult.dto.TemplateResultDTO;
import tech.maslov.rgenerator.domain.templateResult.entity.TemplateResultEntity;

public class TemplateResultMapper implements ExampleDataMapper<TemplateResultEntity, TemplateResultDTO> {

    @Override
    public TemplateResultDTO map(TemplateResultEntity entity) {
        return TemplateResultDTO.builder()
                .id(entity.getId())

                .userId(entity.getUserId())
                .generatorId(entity.getGeneratorId())
                .resultFileId(entity.getResultFileId())
                .content(entity.getContent())
                .status(entity.getStatus())

                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .timeZone(entity.getTimeZone())

                .build();
    }

    @Override
    public TemplateResultEntity inverseMap(TemplateResultDTO dto) {
        return TemplateResultEntity.builder()
                .id(dto.getId())

                .userId(dto.getUserId())
                .generatorId(dto.getGeneratorId())
                .resultFileId(dto.getResultFileId())
                .content(dto.getContent())
                .status(dto.getStatus())

                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .timeZone(dto.getTimeZone())

                .build();
    }
}