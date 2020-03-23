package tech.maslov.rgenerator.templateResult.models;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import com.ub.core.base.models.BaseModel;


import javax.persistence.Id;

@Document
public class TemplateResultDoc extends BaseModel {
    @Id
    private ObjectId id;
    private ObjectId userId;
	private ObjectId generatorId;
	private ObjectId resultFileId;
	private String content;
	
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
    
    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public ObjectId getGeneratorId() {
        return generatorId;
    }

    public void setGeneratorId(ObjectId generatorId) {
        this.generatorId = generatorId;
    }

    public ObjectId getResultFileId() {
        return resultFileId;
    }

    public void setResultFileId(ObjectId resultFileId) {
        this.resultFileId = resultFileId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
