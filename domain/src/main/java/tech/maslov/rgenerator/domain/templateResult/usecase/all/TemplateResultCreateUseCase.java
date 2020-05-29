package tech.maslov.rgenerator.domain.templateResult.usecase.all;

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

import java.io.IOException;

public class TemplateResultCreateUseCase extends TemplateResultBaseUseCase {

    private final GeneratorRepository generatorRepository;
    private final TemplateResultRepository templateResultRepository;
    private final ZipService zipService;

    public TemplateResultCreateUseCase(GeneratorRepository generatorRepository, TemplateResultRepository templateResultRepository, ZipService zipService, AuthorizationDevByTokenUseCase authorizationDevByTokenUseCase, DeveloperRepository developerRepository, UserRepository userRepository) {
        super(templateResultRepository, authorizationDevByTokenUseCase, developerRepository, userRepository);
        this.generatorRepository = generatorRepository;
        this.templateResultRepository = templateResultRepository;
        this.zipService = zipService;
    }

    public TemplateResultEntity create(String generateId, String content) throws AuthenticationException, AuthorizationException {
        GeneratorEntity generatorEntity = generatorRepository.findById(generateId).get();
        this.checkViewAccess(generatorEntity);

        TemplateResultEntity templateResultEntity = new TemplateResultEntity();
        templateResultEntity.setContent(content);
        templateResultEntity.setGeneratorId(generateId);
        templateResultEntity.setUserId(currentDeveloper().getId());
        templateResultRepository.save(templateResultEntity);

        templateResultEntity.setStatus(TemplateResultEntity.Status.IN_PROGRESS);
        templateResultRepository.save(templateResultEntity);
        try {
            templateResultEntity = zipService.generate(generatorEntity, templateResultEntity);
            templateResultEntity.setStatus(TemplateResultEntity.Status.SUCCESS);
            templateResultRepository.save(templateResultEntity);
        } catch (IOException e) {
            e.printStackTrace();
            templateResultEntity.setStatus(TemplateResultEntity.Status.ERROR);
            templateResultRepository.save(templateResultEntity);
        }

        return templateResultEntity;
    }
}
