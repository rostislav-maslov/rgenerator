package tech.maslov.rgenerator.domain.generator.port;

import com.rcore.domain.base.port.CRUDRepository;
import tech.maslov.rgenerator.domain.generator.entity.GeneratorEntity;

public interface GeneratorRepository extends CRUDRepository<String, GeneratorEntity> {

}