package tech.maslov.rgenerator.templateResult.api.response;

import org.bson.types.ObjectId;
import tech.maslov.rgenerator.templateResult.models.TemplateResultDoc;

public class TemplateResultResponse {
    private String id;
    private String generateId;
    private String resultFileId;
    private TemplateResultDoc.Status status = TemplateResultDoc.Status.NEW;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGenerateId() {
        return generateId;
    }

    public void setGenerateId(String generateId) {
        this.generateId = generateId;
    }

    public String getResultFileId() {
        return resultFileId;
    }

    public void setResultFileId(String resultFileId) {
        this.resultFileId = resultFileId;
    }

    public TemplateResultDoc.Status getStatus() {
        return status;
    }

    public void setStatus(TemplateResultDoc.Status status) {
        this.status = status;
    }
}
