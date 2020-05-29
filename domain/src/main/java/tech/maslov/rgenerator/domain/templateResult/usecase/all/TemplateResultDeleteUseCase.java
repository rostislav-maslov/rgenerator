package tech.maslov.rgenerator.domain.templateResult.usecase.all;

import com.rcore.domain.file.port.FileRepository;
import com.rcore.domain.file.port.FileStorage;
import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.user.port.UserRepository;
import tech.maslov.rgenerator.domain.developer.port.DeveloperRepository;
import tech.maslov.rgenerator.domain.developer.usecase.all.AuthorizationDevByTokenUseCase;
import tech.maslov.rgenerator.domain.generator.entity.GeneratorEntity;
import tech.maslov.rgenerator.domain.generator.port.GeneratorRepository;
import tech.maslov.rgenerator.domain.templateResult.entity.TemplateResultEntity;
import tech.maslov.rgenerator.domain.templateResult.port.TemplateResultRepository;
import tech.maslov.rgenerator.domain.templateResult.service.ZipService;

import java.util.List;

public class TemplateResultDeleteUseCase extends TemplateResultBaseUseCase {

    private final GeneratorRepository generatorRepository;
    private final TemplateResultRepository templateResultRepository;
    private final ZipService zipService;
    private final FileStorage fileStorage;
    private final FileRepository fileRepository;

    public TemplateResultDeleteUseCase(GeneratorRepository generatorRepository, TemplateResultRepository templateResultRepository, ZipService zipService, AuthorizationDevByTokenUseCase authorizationDevByTokenUseCase, DeveloperRepository developerRepository, UserRepository userRepository, FileStorage fileStorage, FileRepository fileRepository) {
        super(templateResultRepository, authorizationDevByTokenUseCase, developerRepository, userRepository);
        this.generatorRepository = generatorRepository;
        this.templateResultRepository = templateResultRepository;
        this.zipService = zipService;
        this.fileStorage = fileStorage;
        this.fileRepository = fileRepository;
    }

    public void deleteByGenerator(GeneratorEntity generatorEntity ) throws AuthenticationException, AuthorizationException {
        List<TemplateResultEntity> templateResults = templateResultRepository.findByGeneratorId(generatorEntity);

        for(TemplateResultEntity templateResultEntity : templateResults){
             fileRepository.deleteById(templateResultEntity.getResultFileId());
             fileStorage.remove(templateResultEntity.getResultFileId());
             templateResultRepository.deleteById(templateResultEntity.getId());
        }

    }
}
