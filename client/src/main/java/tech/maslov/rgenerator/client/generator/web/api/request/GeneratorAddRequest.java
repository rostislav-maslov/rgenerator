package tech.maslov.rgenerator.client.generator.web.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneratorAddRequest {
    private String title;
    private String description;
    private String example;
}
