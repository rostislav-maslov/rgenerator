package tech.maslov.rgenerator.client.generator.web.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@ApiModel("RGenerator Object")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GeneratorWeb {

    public enum FileType{
        TEXT, OTHER
    }

    @ApiModel("File Object")
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class File{

        @ApiModelProperty("File id")
        private String fileId;
        @ApiModelProperty("Path")
        private String path;
        @ApiModelProperty("Content")
        private String content;
        @ApiModelProperty("Type")
        private FileType type = FileType.OTHER;

    }

    @ApiModelProperty("Id")
    private String id;
    @ApiModelProperty("Title")
    private String title;
    @ApiModelProperty("Description")
    private String description;
    @ApiModelProperty("Example of JSON")
    private String example;
    @ApiModelProperty("Owner login")
    private String loginOwner;
    @ApiModelProperty("Owner ID")
    private String ownerId;
    @ApiModelProperty("File list")
    private List<File> files = new ArrayList<>();
}
