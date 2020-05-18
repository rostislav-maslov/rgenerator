package tech.maslov.rgenerator.adapter.generator.dto;

import com.rcore.adapter.domain.base.BaseEntityDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import tech.maslov.rgenerator.domain.generator.entity.FileStructure;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
public class GeneratorDTO extends BaseEntityDTO {
    protected String id;

    protected String userId = null;
    protected String title = null;
    protected String description = null;
    protected String example = null;
    protected FileStructure fileStructure = new FileStructure();
}