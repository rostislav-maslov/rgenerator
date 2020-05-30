package tech.maslov.rgenerator.domain.developer.service.github.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentResponse {

    @Getter
    @Setter
    public static class Link {
        private String self;
        private String git;
        private String html;
    }

    private String name;
    private String path;
    private String sha;
    private Long size;
    private String url;
    private String html_url;
    private String git_url;
    private String download_url;
    private String type;//": "file",
    private Link _links;
}

