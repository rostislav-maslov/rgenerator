package tech.maslov.rgenerator.adapter.developer.dto;

import com.rcore.adapter.domain.base.BaseEntityDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
public class DeveloperDTO extends BaseEntityDTO {
    private String id;
}