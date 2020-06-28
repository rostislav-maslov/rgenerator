package tech.maslov.rgenerator.client.generator.web.api.request;

import lombok.Getter;
import lombok.Setter;
import tech.maslov.rgenerator.domain.generator.entity.GeneratorEntity;

@Getter
@Setter
public class GeneratorInfoRequest {
    private String title;
    private String description;
    private GeneratorEntity.AccessLevel accessLevel;
}
