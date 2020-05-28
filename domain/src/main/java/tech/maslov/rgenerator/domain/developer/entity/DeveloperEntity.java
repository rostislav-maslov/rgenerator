package tech.maslov.rgenerator.domain.developer.entity;

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
public class DeveloperEntity extends BaseEntity {
    protected String id;
    protected String picId = null;
}
