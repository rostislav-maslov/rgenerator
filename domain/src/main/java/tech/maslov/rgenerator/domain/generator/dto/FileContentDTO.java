package tech.maslov.rgenerator.domain.generator.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileContentDTO {
    public enum FileType{
        TEXT, OTHER
    }
    private String content;
    private FileType type;
    private String name;
    private String fileId;
    private String path;
}
