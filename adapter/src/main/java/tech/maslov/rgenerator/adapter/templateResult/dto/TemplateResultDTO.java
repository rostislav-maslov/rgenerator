package tech.maslov.rgenerator.adapter.templateResult.dto;

import com.rcore.adapter.domain.base.BaseEntityDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import tech.maslov.rgenerator.domain.templateResult.entity.TemplateResultEntity;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
public class TemplateResultDTO extends BaseEntityDTO {
    private String id;

    protected String userId = null;
    protected String generatorId = null;
    protected String resultFileId = null;
    protected String content = null;
    protected TemplateResultEntity.Status status = TemplateResultEntity.Status.NEW;
}