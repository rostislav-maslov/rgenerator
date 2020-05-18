package tech.maslov.rgenerator.database.mongo.generator.port;

import com.rcore.database.mongo.common.utils.CollectionNameUtils;
import com.rcore.domain.base.port.SearchRequest;
import com.rcore.domain.base.port.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import tech.maslov.rgenerator.domain.generator.entity.GeneratorEntity;
import tech.maslov.rgenerator.domain.generator.port.GeneratorRepository;
import tech.maslov.rgenerator.database.mongo.generator.model.GeneratorDoc;
import tech.maslov.rgenerator.database.mongo.generator.query.FindAllWithSearch;

import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class GeneratorRepositoryImpl implements GeneratorRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public GeneratorEntity save(GeneratorEntity entity) {
        return mongoTemplate.save(entity);
    }

    @Override
    public Boolean delete(GeneratorEntity entity) {
        Long deletedCount = mongoTemplate.remove(entity, CollectionNameUtils.getCollectionName(GeneratorDoc.class)).getDeletedCount();
        return deletedCount > 0;
    }

    @Override
    public Boolean deleteById(String id) {
        Long deletedCount = mongoTemplate.remove(Query.query(Criteria.where("_id").is(id)), CollectionNameUtils.getCollectionName(GeneratorDoc.class)).getDeletedCount();
        return deletedCount > 0;
    }

    @Override
    public Optional<GeneratorEntity> findById(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, GeneratorDoc.class));
    }

    @Override
    public SearchResult<GeneratorEntity> find(SearchRequest request) {
        Query query = new FindAllWithSearch(request).getQuery();
        return SearchResult.withItemsAndCount(
                mongoTemplate.find(query, GeneratorDoc.class)
                        .stream()
                        .collect(Collectors.toList()),
                mongoTemplate.count(query, GeneratorDoc.class)
        );
    }

    @Override
    public Long count() {
        return mongoTemplate.count(new Query(), GeneratorDoc.class);
    }
}