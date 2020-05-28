package tech.maslov.rgenerator.database.mongo.developer.port;

import com.rcore.database.mongo.common.utils.CollectionNameUtils;
import com.rcore.domain.base.port.SearchRequest;
import com.rcore.domain.base.port.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import tech.maslov.rgenerator.database.mongo.developer.model.DeveloperDoc;
import tech.maslov.rgenerator.database.mongo.developer.query.FindAllWithSearch;
import tech.maslov.rgenerator.domain.developer.entity.DeveloperEntity;
import tech.maslov.rgenerator.domain.developer.port.DeveloperRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class DeveloperRepositoryImpl implements DeveloperRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public DeveloperEntity save(DeveloperEntity entity) {
        return mongoTemplate.save(entity, CollectionNameUtils.getCollectionName(DeveloperDoc.class));
    }

    @Override
    public Boolean delete(DeveloperEntity entity) {
        Long deletedCount = mongoTemplate.remove(entity, CollectionNameUtils.getCollectionName(DeveloperDoc.class)).getDeletedCount();
        return deletedCount > 0;
    }

    @Override
    public Boolean deleteById(String id) {
        Long deletedCount = mongoTemplate.remove(Query.query(Criteria.where("_id").is(id)), CollectionNameUtils.getCollectionName(DeveloperDoc.class)).getDeletedCount();
        return deletedCount > 0;
    }

    @Override
    public Optional<DeveloperEntity> findById(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, DeveloperDoc.class));
    }

    @Override
    public SearchResult<DeveloperEntity> find(SearchRequest request) {
        Query query = new FindAllWithSearch(request).getQuery();
        return SearchResult.withItemsAndCount(
                mongoTemplate.find(query, DeveloperDoc.class)
                        .stream()
                        .collect(Collectors.toList()),
                mongoTemplate.count(query, DeveloperDoc.class)
        );
    }

    @Override
    public Long count() {
        return mongoTemplate.count(new Query(), DeveloperDoc.class);
    }
}