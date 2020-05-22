package tech.maslov.rgenerator.client.templateResult.web.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import tech.maslov.rgenerator.domain.templateResult.entity.TemplateResultEntity;

@Getter
@Setter
@Builder
public class TemplateResultResponse {
    private String id;
    private String generateId;
    private String resultFileId;
    private TemplateResultEntity.Status status = TemplateResultEntity.Status.NEW;
}
