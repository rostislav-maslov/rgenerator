package tech.maslov.rgenerator.database.mongo.templateResult.query;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import tech.maslov.rgenerator.domain.generator.entity.GeneratorEntity;

public class FindAllByGeneratorId{

    private GeneratorEntity generatorEntity;

    public FindAllByGeneratorId(GeneratorEntity generatorEntity) {
        this.generatorEntity = generatorEntity;
    }

    public Criteria getCriteria() {
        return Criteria.where("generatorId").is(generatorEntity.getId());
    }

    public Query getQuery() {
        return Query.query(this.getCriteria());
    }
}
