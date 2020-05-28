package tech.maslov.rgenerator.domain.developer.port;

import com.rcore.domain.base.port.CRUDRepository;
import tech.maslov.rgenerator.domain.developer.entity.DeveloperEntity;

public interface DeveloperRepository extends CRUDRepository<String, DeveloperEntity> {

}