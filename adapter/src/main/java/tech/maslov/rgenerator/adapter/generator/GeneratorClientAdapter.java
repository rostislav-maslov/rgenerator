package tech.maslov.rgenerator.adapter.generator;

import com.rcore.domain.file.entity.FileEntity;
import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.exception.AuthorizationException;
import lombok.RequiredArgsConstructor;
import tech.maslov.rgenerator.adapter.generator.dto.GeneratorDTO;
import tech.maslov.rgenerator.adapter.generator.mapper.GeneratorMapper;
import tech.maslov.rgenerator.adapter.generator.mapper.GeneratorWithOwnerMapper;
import tech.maslov.rgenerator.domain.developer.service.github.dto.RepoResponse;
import tech.maslov.rgenerator.domain.generator.config.GeneratorConfig;
import tech.maslov.rgenerator.domain.generator.dto.CheckRepoDTO;
import tech.maslov.rgenerator.domain.generator.dto.FileContentDTO;
import tech.maslov.rgenerator.domain.generator.entity.FileStructure;
import tech.maslov.rgenerator.domain.generator.entity.GeneratorEntity;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class GeneratorClientAdapter {
    private final GeneratorConfig config;
    private final GeneratorWithOwnerMapper mapper = new GeneratorWithOwnerMapper();

    public GeneratorDTO create(String title, String description, String example, GeneratorEntity.AccessLevel accessLevel) throws AuthenticationException {
        return mapper.map(config.all.createUseCase()
                .create(title, description, example, accessLevel));
    }

    public GeneratorDTO editInfo(String id, String title, String description, GeneratorEntity.AccessLevel accessLevel) throws AuthenticationException, AuthorizationException {
        return mapper.map(config.all.editUseCase().editInfo(id, title, description, accessLevel));
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

    public List<GeneratorDTO> list() {
        return mapper.mapAll(config.all.viewUseCase().findAll());
    }

    public List<GeneratorDTO> myGeneratorsList() throws AuthenticationException {
        return mapper.mapAll(config.all.viewUseCase().findMyGenerators());
    }

    public void delete(String generatorId) throws AuthenticationException, AuthorizationException {
        config.all.deleteUseCase().delete(generatorId);
    }

    public CheckRepoDTO checkRepo(String repoName) throws IOException, AuthenticationException {
        return config.all.generatorGitHubUseCase().checkRepo(repoName);
    }

    public List<RepoResponse> repos() throws IOException, AuthenticationException {
        return config.all.generatorGitHubUseCase().repos();
    }

    public void syncRepo(String generatorId, String repoName) throws IOException, AuthenticationException, InterruptedException, AuthorizationException {
        config.all.generatorGitHubUseCase().syncRepo(generatorId, repoName);
    }


}
