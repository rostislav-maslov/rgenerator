package tech.maslov.rgenerator.adapter.generator.mapper;

import com.rcore.commons.mapper.ExampleDataMapper;
import tech.maslov.rgenerator.adapter.generator.dto.GeneratorDTO;
import tech.maslov.rgenerator.domain.generator.dto.GeneratorWithOwnerDTO;

public class GeneratorWithOwnerMapper implements ExampleDataMapper<GeneratorWithOwnerDTO, GeneratorDTO> {

    @Override
    public GeneratorDTO map(GeneratorWithOwnerDTO entity) {
        return GeneratorDTO.builder()
                .id(entity.getId())

                .ownerId(entity.getOwnerId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .example(entity.getExample())
                .fileStructure(entity.getFileStructure())
                .didUseGitHub(entity.getDidUseGitHub())
                .countOfUse(entity.getCountOfUse())

                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .timeZone(entity.getTimeZone())

                .loginOwner(entity.getLoginOwner())

                .accessLevel(entity.getAccessLevel())

                .build();
    }

    @Override
    public GeneratorWithOwnerDTO inverseMap(GeneratorDTO dto) {
        return GeneratorWithOwnerDTO.builder()
                .id(dto.getId())

                .ownerId(dto.getOwnerId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .example(dto.getExample())
                .fileStructure(dto.getFileStructure())

                .loginOwner(dto.getLoginOwner())

                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .timeZone(dto.getTimeZone())

                .accessLevel(dto.getAccessLevel())

                .build();
    }
}