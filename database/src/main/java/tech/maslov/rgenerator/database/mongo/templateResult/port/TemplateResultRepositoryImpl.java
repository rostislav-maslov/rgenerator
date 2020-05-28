package tech.maslov.rgenerator.database.mongo.templateResult.port;

import com.rcore.database.mongo.common.utils.CollectionNameUtils;
import com.rcore.domain.base.port.SearchRequest;
import com.rcore.domain.base.port.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import tech.maslov.rgenerator.domain.templateResult.entity.TemplateResultEntity;
import tech.maslov.rgenerator.domain.templateResult.port.TemplateResultRepository;
import tech.maslov.rgenerator.database.mongo.templateResult.model.TemplateResultDoc;
import tech.maslov.rgenerator.database.mongo.templateResult.query.FindAllWithSearch;

import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class TemplateResultRepositoryImpl implements TemplateResultRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public TemplateResultEntity save(TemplateResultEntity entity) {
        return mongoTemplate.save(entity, CollectionNameUtils.getCollectionName(TemplateResultDoc.class));
    }

    @Override
    public Boolean delete(TemplateResultEntity entity) {
        Long deletedCount = mongoTemplate.remove(entity, CollectionNameUtils.getCollectionName(TemplateResultDoc.class)).getDeletedCount();
        return deletedCount > 0;
    }

    @Override
    public Boolean deleteById(String id) {
        Long deletedCount = mongoTemplate.remove(Query.query(Criteria.where("_id").is(id)), CollectionNameUtils.getCollectionName(TemplateResultDoc.class)).getDeletedCount();
        return deletedCount > 0;
    }

    @Override
    public Optional<TemplateResultEntity> findById(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, TemplateResultDoc.class));
    }

    @Override
    public SearchResult<TemplateResultEntity> find(SearchRequest request) {
        Query query = new FindAllWithSearch(request).getQuery();
        return SearchResult.withItemsAndCount(
                mongoTemplate.find(query, TemplateResultDoc.class)
                        .stream()
                        .collect(Collectors.toList()),
                mongoTemplate.count(query, TemplateResultDoc.class)
        );
    }

    @Override
    public Long count() {
        return mongoTemplate.count(new Query(), TemplateResultDoc.class);
    }
}