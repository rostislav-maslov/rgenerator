package tech.maslov.rgenerator.generator.api.response;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class GeneratorResponse {
    public enum FileType{
        TEXT, OTHER
    }
    public static class File{

        private String fileId;
        private String path;
        private String content;
        private FileType type = FileType.OTHER;

        public String getFileId() {
            return fileId;
        }

        public void setFileId(String fileId) {
            this.fileId = fileId;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public FileType getType() {
            return type;
        }

        public void setType(FileType type) {
            this.type = type;
        }

    }

    private String id;
    private String title;
    private String description;
    private String example;
    private List<File> files = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}
