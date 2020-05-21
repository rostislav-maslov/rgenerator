package tech.maslov.rgenerator.client.generator.application;

import com.rcore.commons.mapper.ExampleDataMapper;
import org.springframework.stereotype.Component;
import tech.maslov.rgenerator.client.generator.web.api.response.GeneratorWeb;
import tech.maslov.rgenerator.domain.generator.dto.FileContentDTO;
import tech.maslov.rgenerator.domain.generator.entity.FileStructure;

import java.util.ArrayList;
import java.util.List;

@Component
public class FileWebMapper implements ExampleDataMapper<FileContentDTO, GeneratorWeb.File> {

    public GeneratorWeb.File map(FileContentDTO file) {
        GeneratorWeb.File fileResponse = new GeneratorWeb.File();

        fileResponse.setFileId(file.getFileId());
        fileResponse.setPath(file.getName());
        fileResponse.setContent(file.getContent());
        fileResponse.setPath(file.getPath());

        return fileResponse;
    }

    public FileContentDTO inverseMap(GeneratorWeb.File orderWeb) {
        throw new RuntimeException("dont use this");
    }
}
