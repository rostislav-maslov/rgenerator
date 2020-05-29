package tech.maslov.rgenerator.domain.templateResult.port;

import com.rcore.domain.base.port.CRUDRepository;
import tech.maslov.rgenerator.domain.generator.entity.GeneratorEntity;
import tech.maslov.rgenerator.domain.templateResult.entity.TemplateResultEntity;

import java.util.List;
import java.util.Optional;

public interface TemplateResultRepository extends CRUDRepository<String, TemplateResultEntity> {
    List<TemplateResultEntity> findByGeneratorId(GeneratorEntity generatorEntity);
}