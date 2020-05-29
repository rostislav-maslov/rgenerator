package tech.maslov.rgenerator.domain.generator.dto;

import com.rcore.domain.user.entity.UserEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import tech.maslov.rgenerator.domain.generator.entity.GeneratorEntity;

@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class GeneratorWithOwnerDTO extends GeneratorEntity {
    private String loginOwner;

    public static GeneratorWithOwnerDTO of(GeneratorEntity generatorEntity, UserEntity userEntity) {
        return GeneratorWithOwnerDTO.builder()
                .loginOwner(userEntity.getLogin())
                .id(generatorEntity.getId())
                .ownerId(generatorEntity.getOwnerId())
                .title(generatorEntity.getTitle())
                .description(generatorEntity.getDescription())
                .example(generatorEntity.getExample())
                .fileStructure(generatorEntity.getFileStructure())
                .build();

    }
}
