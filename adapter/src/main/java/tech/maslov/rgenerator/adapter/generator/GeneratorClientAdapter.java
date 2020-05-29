package tech.maslov.rgenerator.adapter.generator;

import com.rcore.domain.file.entity.FileEntity;
import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.exception.AuthorizationException;
import lombok.RequiredArgsConstructor;
import tech.maslov.rgenerator.adapter.generator.dto.GeneratorDTO;
import tech.maslov.rgenerator.adapter.generator.mapper.GeneratorMapper;
import tech.maslov.rgenerator.domain.generator.config.GeneratorConfig;
import tech.maslov.rgenerator.domain.generator.dto.FileContentDTO;
import tech.maslov.rgenerator.domain.generator.entity.FileStructure;

import java.util.List;

@RequiredArgsConstructor
public class GeneratorClientAdapter {
    private final GeneratorConfig config;
    private final GeneratorMapper mapper = new GeneratorMapper();

    public GeneratorDTO create(String title, String description, String example) throws AuthenticationException, AuthorizationException {
        return mapper.map(config.all.createUseCase()
                .create(title, description, example));
    }

    public GeneratorDTO editInfo(String id, String title, String description) throws AuthenticationException, AuthorizationException {
        return mapper.map(config.all.editUseCase().editInfo(id, title, description));
    }

    public GeneratorDTO editFile(String id, String oldFile, String newFile) throws AuthenticationException, AuthorizationException {
        return mapper.map(config.all.editUseCase().editFile(id, oldFile, newFile));
    }

    public GeneratorDTO editJson(String id, String example) throws AuthenticationException, AuthorizationException {
        return mapper.map(config.all.editUseCase().editJson(id, example));
    }

    public GeneratorDTO byId(String id) throws AuthenticationException, AuthorizationException {
        return mapper.map(config.all.viewUseCase().findId(id));
    }

    public GeneratorDTO fileDelete(String id, String fileId) throws AuthenticationException, AuthorizationException {
        return mapper.map(config.all.editUseCase().removeFile(id, fileId));
    }

    public FileContentDTO file(String id, String fileId) throws AuthenticationException, AuthorizationException {
        return config.all.viewUseCase().fileView(id, fileId);
    }

    public FileContentDTO fileAdd(String id, String path, FileEntity fileEntity) throws AuthenticationException, AuthorizationException {
        return config.all.editUseCase().fileAdd(id, path, fileEntity);
    }

    public List<GeneratorDTO> list(){
        return mapper.mapAll(config.all.viewUseCase().findAll());
    }


}
