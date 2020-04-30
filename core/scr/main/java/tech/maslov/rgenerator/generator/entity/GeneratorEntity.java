package tech.maslov.rgenerator.generator.entity;

import com.rcore.domain.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GeneratorEntity extends BaseEntity {
    protected String id;
    protected String userId = null;
    protected String title = null;
    protected String description = null;
    protected String example = null;
    protected FileStructure fileStructure = new FileStructure();
}
