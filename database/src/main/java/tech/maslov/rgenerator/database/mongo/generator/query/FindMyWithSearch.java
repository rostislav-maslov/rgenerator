package tech.maslov.rgenerator.database.mongo.generator.query;

import com.rcore.database.mongo.common.query.AbstractExampleQuery;
import com.rcore.domain.base.port.SearchRequest;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import tech.maslov.rgenerator.domain.developer.entity.DeveloperEntity;
import tech.maslov.rgenerator.domain.generator.entity.GeneratorEntity;

public class FindMyWithSearch extends AbstractExampleQuery<GeneratorEntity> {

    private DeveloperEntity developerEntity;

    public FindMyWithSearch(DeveloperEntity developerEntity, SearchRequest request) {
        super(request);
        this.developerEntity = developerEntity;
    }

    @Override
    public Criteria getCriteria() {
        Criteria criteria = Criteria.where("ownerId").is(developerEntity.getId());
        return criteria;
    }
}
