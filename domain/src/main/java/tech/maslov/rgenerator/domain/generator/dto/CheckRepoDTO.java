package tech.maslov.rgenerator.domain.generator.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckRepoDTO {
    private Boolean exampleJson = false;
    private Boolean rootDir = false;
}
