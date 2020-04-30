package tech.maslov.rgenerator.templateResult.entity;

import com.rcore.domain.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class TemplateResultEntity extends BaseEntity {
    public enum Status{
        NEW, IN_PROGRESS, SUCCESS, ERROR
    }

    protected String id;
    protected String userId = null;
    protected String generatorId = null;
    protected String resultFileId = null;
    protected String content = null;
    protected Status status = Status.NEW;
}
