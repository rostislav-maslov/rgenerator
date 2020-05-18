package tech.maslov.rgenerator.database.mongo.templateResult.port;

import com.rcore.database.mongo.base.ObjectIdGenerator;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import tech.maslov.rgenerator.domain.templateResult.port.TemplateResultIdGenerator;

@Component
public class TemplateResultIdGeneratorImpl extends ObjectIdGenerator implements TemplateResultIdGenerator<ObjectId> {
}
