package tech.maslov.rgenerator.generator.models;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import com.ub.core.base.models.BaseModel;


import javax.persistence.Id;

@Document
public class GeneratorDoc extends BaseModel {
    @Id
    private ObjectId id;
    private ObjectId userId;
    private String title = "";
    private String description = "";
    private String example = "";
    private FileStructure fileStructure = new FileStructure();

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public FileStructure getFileStructure() {
        return fileStructure;
    }

    public void setFileStructure(FileStructure fileStructure) {
        this.fileStructure = fileStructure;
    }

}
