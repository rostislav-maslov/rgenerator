package tech.maslov.rgenerator.adapter.generator.mapper;

import com.rcore.commons.mapper.ExampleDataMapper;
import tech.maslov.rgenerator.adapter.generator.dto.GeneratorDTO;
import tech.maslov.rgenerator.domain.generator.entity.GeneratorEntity;

public class GeneratorMapper implements ExampleDataMapper<GeneratorEntity, GeneratorDTO> {

    @Override
    public GeneratorDTO map(GeneratorEntity entity) {
        return GeneratorDTO.builder()
                .id(entity.getId())

                .userId(entity.getUserId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .example(entity.getExample())
                .fileStructure(entity.getFileStructure())

                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .timeZone(entity.getTimeZone())


                .build();
    }

    @Override
    public GeneratorEntity inverseMap(GeneratorDTO dto) {
        return GeneratorEntity.builder()
                .id(dto.getId())

                .userId(dto.getUserId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .example(dto.getExample())
                .fileStructure(dto.getFileStructure())

                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .timeZone(dto.getTimeZone())
                .build();
    }
}