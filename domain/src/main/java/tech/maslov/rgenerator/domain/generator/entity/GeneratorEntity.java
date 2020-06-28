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
    public enum AccessLevel{
        PUBLIC, PRIVATE
    }
    protected String id;
    protected String ownerId = null;
    protected String title = null;
    protected String description = null;
    protected String example = null;
    protected FileStructure fileStructure = new FileStructure();
    protected Long countOfUse = 1l;
    protected Boolean didUseGitHub = false;
    protected AccessLevel accessLevel = AccessLevel.PUBLIC;
}
