package tech.maslov.rgenerator.generator.models;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class FileStructure {
    public static class File {
        private String name;
        private ObjectId fileId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ObjectId getFileId() {
            return fileId;
        }

        public void setFileId(ObjectId fileId) {
            this.fileId = fileId;
        }
    }

    public static class Directory {
        private String name;
        private List<File> files = new ArrayList<>();
        private List<Directory> directories = new ArrayList<>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<File> getFiles() {
            return files;
        }

        public void setFiles(List<File> files) {
            this.files = files;
        }

        public List<Directory> getDirectories() {
            return directories;
        }

        public void setDirectories(List<Directory> directories) {
            this.directories = directories;
        }
    }

    private Directory directory = new Directory();

    public Directory getDirectory() {
        return directory;
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }
}
