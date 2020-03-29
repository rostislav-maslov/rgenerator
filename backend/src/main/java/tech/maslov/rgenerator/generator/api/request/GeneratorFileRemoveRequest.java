package tech.maslov.rgenerator.generator.api.request;

import org.bson.types.ObjectId;

public class GeneratorFileRemoveRequest {
    private ObjectId fileId;

    public ObjectId getFileId() {
        return fileId;
    }

    public void setFileId(ObjectId fileId) {
        this.fileId = fileId;
    }

}
