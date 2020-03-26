package tech.maslov.rgenerator.templateResult.api.request;

import org.bson.types.ObjectId;

public class TemplateResultCreateRequest {
    private ObjectId generateId;
    private String content;

    public ObjectId getGenerateId() {
        return generateId;
    }

    public void setGenerateId(ObjectId generateId) {
        this.generateId = generateId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
