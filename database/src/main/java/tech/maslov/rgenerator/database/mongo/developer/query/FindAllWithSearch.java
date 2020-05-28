package tech.maslov.rgenerator.database.mongo.developer.query;

import com.rcore.database.mongo.common.query.AbstractExampleQuery;
import com.rcore.domain.base.port.SearchRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import tech.maslov.rgenerator.domain.developer.entity.DeveloperEntity;

public class FindAllWithSearch extends AbstractExampleQuery<DeveloperEntity> {

    public FindAllWithSearch(SearchRequest request) {
        super(request);
    }

    @Override
    public Criteria getCriteria() {
        return new Criteria();
    }
}
