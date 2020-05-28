package tech.maslov.rgenerator.domain.generator.entity;

import com.rcore.domain.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import tech.maslov.rgenerator.domain.developer.entity.DeveloperEntity;

import java.util.*;

@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
@Data
public class GeneratorEntity extends BaseEntity {
    protected String id;
    protected String userId = null;
    protected String title = null;
    protected String description = null;
    protected String example = null;
    protected FileStructure fileStructure = new FileStructure();

    protected String developerId;
}
