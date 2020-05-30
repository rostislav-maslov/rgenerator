package tech.maslov.rgenerator.client.generator.application;

import com.rcore.commons.mapper.ExampleDataMapper;
import org.springframework.stereotype.Component;
import tech.maslov.rgenerator.adapter.generator.dto.GeneratorDTO;
import tech.maslov.rgenerator.client.generator.web.api.response.GeneratorWeb;
import tech.maslov.rgenerator.domain.generator.entity.FileStructure;

import java.util.ArrayList;
import java.util.List;

@Component
public class GeneratorWebMapper implements ExampleDataMapper<GeneratorDTO, GeneratorWeb> {

    public GeneratorWeb map(GeneratorDTO generatorDTO) {
        return GeneratorWeb.builder()
                .id(generatorDTO.getId())

                .title(generatorDTO.getTitle())
                .description(generatorDTO.getDescription())
                .example(generatorDTO.getExample())
                .loginOwner(generatorDTO.getLoginOwner())
                .ownerId(generatorDTO.getOwnerId())
                .didUseGitHub(generatorDTO.getDidUseGitHub())
                .countOfUse(generatorDTO.getCountOfUse())
                .files(transformFiles(generatorDTO.getFileStructure().getDirectory(), ""))

                .build();
    }

    private List<GeneratorWeb.File> transformFiles(FileStructure.Directory dir, String parentPath) {
        List<GeneratorWeb.File> response = new ArrayList<>();

        for (FileStructure.File file : dir.getFiles()) {
            GeneratorWeb.File responseFile = new GeneratorWeb.File();
            responseFile.setFileId(file.getFileId());
            responseFile.setPath(parentPath + "/" + dir.getName() + "/" + file.getName());

            response.add(responseFile);
        }

        for (FileStructure.Directory childDir : dir.getDirectories()) {
            response.addAll(
                    transformFiles(childDir, parentPath + "/" + dir.getName())
            );
        }

        return response;
    }

    public GeneratorDTO inverseMap(GeneratorWeb generatorWeb) {
        throw new RuntimeException("dont use this");
    }
}
