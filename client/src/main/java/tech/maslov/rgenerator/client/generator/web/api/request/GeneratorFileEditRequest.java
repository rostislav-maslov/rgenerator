package tech.maslov.rgenerator.client.generator.web.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneratorFileEditRequest {
    private String newFileId;
    private String oldFileId;
}
