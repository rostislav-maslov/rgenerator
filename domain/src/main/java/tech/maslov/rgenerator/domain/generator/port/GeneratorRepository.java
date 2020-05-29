package tech.maslov.rgenerator.domain.generator.port;

import com.rcore.domain.base.port.CRUDRepository;
import com.rcore.domain.base.port.SearchRequest;
import com.rcore.domain.base.port.SearchResult;
import tech.maslov.rgenerator.domain.developer.entity.DeveloperEntity;
import tech.maslov.rgenerator.domain.generator.entity.GeneratorEntity;

public interface GeneratorRepository extends CRUDRepository<String, GeneratorEntity> {
    SearchResult<GeneratorEntity> findMyGenerators(DeveloperEntity developerEntity, SearchRequest request);
}