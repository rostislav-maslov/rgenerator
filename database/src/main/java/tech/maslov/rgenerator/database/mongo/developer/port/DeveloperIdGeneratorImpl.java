package tech.maslov.rgenerator.database.mongo.developer.port;

import com.rcore.database.mongo.base.ObjectIdGenerator;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import tech.maslov.rgenerator.domain.developer.port.DeveloperIdGenerator;

@Component
public class DeveloperIdGeneratorImpl extends ObjectIdGenerator implements DeveloperIdGenerator<ObjectId> {
}
