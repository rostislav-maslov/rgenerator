package tech.maslov.rgenerator.database.mongo.generator.query;

import com.rcore.database.mongo.common.query.AbstractExampleQuery;
import com.rcore.domain.base.port.SearchRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import tech.maslov.rgenerator.domain.generator.entity.GeneratorEntity;

public class FindAllWithSearch extends AbstractExampleQuery<GeneratorEntity> {

    public FindAllWithSearch(SearchRequest request) {
        super(request);
    }

    @Override
    public Criteria getCriteria() {
        return new Criteria();
    }
}
