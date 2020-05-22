package tech.maslov.rgenerator.client.templateResult.application;

import com.rcore.commons.mapper.ExampleDataMapper;
import org.springframework.stereotype.Component;
import tech.maslov.rgenerator.adapter.templateResult.dto.TemplateResultDTO;
import tech.maslov.rgenerator.client.templateResult.web.api.TemplateResultResponse;

@Component
public class TemplateWebMapper implements ExampleDataMapper<TemplateResultDTO, TemplateResultResponse> {

    public TemplateResultResponse map(TemplateResultDTO templateResultEntity) {
        return TemplateResultResponse.builder()

                .id(templateResultEntity.getId())
                .generateId(templateResultEntity.getGeneratorId())
                .resultFileId(templateResultEntity.getResultFileId())
                .status(templateResultEntity.getStatus())

                .build();
    }


    public TemplateResultDTO inverseMap(TemplateResultResponse generatorWeb) {
        throw new RuntimeException("dont use this");
    }
}

