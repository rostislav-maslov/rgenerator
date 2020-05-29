package tech.maslov.rgenerator.domain.templateResult.config;

import com.rcore.domain.file.port.FileRepository;
import com.rcore.domain.file.port.FileStorage;
import com.rcore.domain.token.usecase.AuthorizationByTokenUseCase;
import com.rcore.domain.user.port.UserRepository;
import tech.maslov.rgenerator.domain.developer.entity.DeveloperEntity;
import tech.maslov.rgenerator.domain.developer.port.DeveloperRepository;
import tech.maslov.rgenerator.domain.developer.usecase.all.AuthorizationDevByTokenUseCase;
import tech.maslov.rgenerator.domain.generator.port.GeneratorRepository;
import tech.maslov.rgenerator.domain.templateResult.port.TemplateResultRepository;
import tech.maslov.rgenerator.domain.templateResult.port.TemplateResultIdGenerator;
import lombok.RequiredArgsConstructor;
import tech.maslov.rgenerator.domain.templateResult.service.ZipService;
import tech.maslov.rgenerator.domain.templateResult.usecase.all.TemplateResultCreateUseCase;
import tech.maslov.rgenerator.domain.templateResult.usecase.secured.*;

public class TemplateResultConfig {

    @RequiredArgsConstructor
    public static class Secured {
        protected final TemplateResultRepository templateResultRepository;
        protected final TemplateResultIdGenerator idGenerator;
        protected final AuthorizationByTokenUseCase authorizationByTokenUseCase;

        public SecuredTemplateResultCreateUseCase createUseCase() {
            return new SecuredTemplateResultCreateUseCase(this.templateResultRepository, idGenerator, this.authorizationByTokenUseCase);
        }

        public SecuredTemplateResultDeleteUseCase deleteUseCase() {
            return new SecuredTemplateResultDeleteUseCase(this.templateResultRepository, this.authorizationByTokenUseCase);
        }

        public SecuredTemplateResultUpdateUseCase updateUseCase() {
            return new SecuredTemplateResultUpdateUseCase(this.templateResultRepository, this.authorizationByTokenUseCase);
        }

        public SecuredTemplateResultViewUseCase viewUseCase() {
            return new SecuredTemplateResultViewUseCase(this.templateResultRepository, this.authorizationByTokenUseCase);
        }
    }

    @RequiredArgsConstructor
    public static class All {
        protected final TemplateResultRepository templateResultRepository;
        protected final GeneratorRepository generatorRepository;
        protected final ZipService zipService;
        protected final DeveloperRepository developerRepository;
        protected final UserRepository userRepository;
        protected final AuthorizationDevByTokenUseCase authorizationDevByTokenUseCase;

        public TemplateResultCreateUseCase createUseCase(){
            return new TemplateResultCreateUseCase(generatorRepository, templateResultRepository, zipService, authorizationDevByTokenUseCase, developerRepository, userRepository);
        }
    }

    protected final TemplateResultRepository templateResultRepository;
    protected final GeneratorRepository generatorRepository;
    protected final TemplateResultIdGenerator idGenerator;
    protected final ZipService zipService;
    protected final AuthorizationByTokenUseCase authorizationByTokenUseCase;
    protected final FileRepository fileRepository;
    protected final FileStorage fileStorage;
    protected final DeveloperRepository developerRepository;
    protected final UserRepository userRepository;
    protected final AuthorizationDevByTokenUseCase authorizationDevByTokenUseCase;

    public final Secured secured;
    public final All all;

    public TemplateResultConfig(TemplateResultRepository templateResultRepository, GeneratorRepository generatorRepository, TemplateResultIdGenerator idGenerator, AuthorizationByTokenUseCase authorizationByTokenUseCase, FileRepository fileRepository, FileStorage fileStorage, AuthorizationDevByTokenUseCase authorizationDevByTokenUseCase, DeveloperRepository developerRepository, UserRepository userRepository) {
        this.templateResultRepository = templateResultRepository;
        this.generatorRepository = generatorRepository;
        this.idGenerator = idGenerator;
        this.authorizationByTokenUseCase = authorizationByTokenUseCase;
        this.fileRepository = fileRepository;
        this.fileStorage = fileStorage;
        this.zipService = new ZipService(fileRepository, fileStorage);
        this.developerRepository = developerRepository;
        this.userRepository = userRepository;
        this.authorizationDevByTokenUseCase = authorizationDevByTokenUseCase;

        this.secured = new Secured(this.templateResultRepository, this.idGenerator, this.authorizationByTokenUseCase);
        this.all = new All(this.templateResultRepository, this.generatorRepository, zipService,  developerRepository, userRepository, authorizationDevByTokenUseCase);
    }

}