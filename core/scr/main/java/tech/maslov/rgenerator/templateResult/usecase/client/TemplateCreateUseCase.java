package tech.maslov.rgenerator.templateResult.usecase.client;

import tech.maslov.rgenerator.generator.entity.GeneratorEntity;
import tech.maslov.rgenerator.generator.port.GeneratorRepository;
import tech.maslov.rgenerator.templateResult.entity.TemplateResultEntity;
import tech.maslov.rgenerator.templateResult.port.TemplateResultRepository;

import java.io.IOException;

public class TemplateCreateUseCase extends TemplateResultBaseUseCase {
    private final GeneratorRepository generatorRepository;
    private final TemplateZipUseCase templateZipUseCase;

    public TemplateCreateUseCase(TemplateResultRepository templateResultRepository, GeneratorRepository generatorRepository, TemplateZipUseCase templateZipUseCase) {
        super(templateResultRepository);
        this.generatorRepository = generatorRepository;
        this.templateZipUseCase = templateZipUseCase;
    }

    public TemplateResultEntity create(String generatorId, String content) {
        GeneratorEntity generator = generatorRepository.findById(generatorId).get();
        TemplateResultEntity templateResultEntity = new TemplateResultEntity();
        templateResultEntity.setContent(content);
        templateResultEntity.setGeneratorId(generatorId);
        templateResultRepository.save(templateResultEntity);

        templateResultEntity.setStatus(TemplateResultEntity.Status.IN_PROGRESS);
        templateResultRepository.save(templateResultEntity);
        try {
            templateResultEntity = templateZipUseCase.generate(generator, templateResultEntity);
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
