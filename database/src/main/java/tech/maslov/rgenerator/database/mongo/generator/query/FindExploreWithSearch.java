package tech.maslov.rgenerator.database.mongo.generator.query;

import com.rcore.database.mongo.common.query.AbstractExampleQuery;
import com.rcore.domain.base.port.SearchRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import tech.maslov.rgenerator.domain.developer.entity.DeveloperEntity;
import tech.maslov.rgenerator.domain.generator.entity.GeneratorEntity;

public class FindExploreWithSearch extends AbstractExampleQuery<GeneratorEntity> {

    private DeveloperEntity developerEntity;

    public FindExploreWithSearch(DeveloperEntity developerEntity, SearchRequest request) {
        super(request);
        this.developerEntity = developerEntity;
    }

    @Override
    public Criteria getCriteria() {
        Criteria criteria;
        if (developerEntity != null) {
            criteria = new Criteria().orOperator(
                    Criteria.where("ownerId").is(developerEntity.getId()),
                    Criteria.where("accessLevel").is(GeneratorEntity.AccessLevel.PUBLIC)
            );
        } else {
            criteria = Criteria.where("accessLevel").is(GeneratorEntity.AccessLevel.PUBLIC);
        }


        return criteria;
    }
}
