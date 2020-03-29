package tech.maslov.rgenerator.generator.api.request;

import org.bson.types.ObjectId;

public class GeneratorFileEditRequest {
    private ObjectId oldFileId;
    private ObjectId newFileId;

    public ObjectId getOldFileId() {
        return oldFileId;
    }

    public void setOldFileId(ObjectId oldFileId) {
        this.oldFileId = oldFileId;
    }

    public ObjectId getNewFileId() {
        return newFileId;
    }

    public void setNewFileId(ObjectId newFileId) {
        this.newFileId = newFileId;
    }
}
