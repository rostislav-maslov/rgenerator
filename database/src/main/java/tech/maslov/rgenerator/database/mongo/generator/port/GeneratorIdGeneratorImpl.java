package tech.maslov.rgenerator.database.mongo.generator.port;

import com.rcore.database.mongo.base.ObjectIdGenerator;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import tech.maslov.rgenerator.domain.generator.port.GeneratorIdGenerator;

@Component
public class GeneratorIdGeneratorImpl extends ObjectIdGenerator implements GeneratorIdGenerator<ObjectId> {
}
