package tech.maslov.rgenerator.domain.templateResult.entity;

import com.rcore.domain.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.*;

@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
@Data
public class TemplateResultEntity extends BaseEntity {
    public enum Status{
        NEW, IN_PROGRESS, SUCCESS, ERROR;
    }
    protected String id;
    protected String userId = null;
    protected String generatorId = null;
    protected String resultFileId = null;
    protected String content = null;
    protected Status status = Status.NEW;
}
