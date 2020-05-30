package tech.maslov.rgenerator.domain.generator.config;

import com.rcore.domain.file.port.FileRepository;
import com.rcore.domain.file.port.FileStorage;
import com.rcore.domain.token.port.AccessTokenStorage;
import com.rcore.domain.token.port.RefreshTokenRepository;
import com.rcore.domain.token.usecase.AuthorizationByTokenUseCase;
import com.rcore.domain.user.port.UserRepository;
import tech.maslov.rgenerator.domain.developer.port.DeveloperRepository;
import tech.maslov.rgenerator.domain.developer.usecase.all.AuthorizationDevByTokenUseCase;
import tech.maslov.rgenerator.domain.generator.port.GeneratorRepository;
import tech.maslov.rgenerator.domain.generator.port.GeneratorIdGenerator;
import lombok.RequiredArgsConstructor;
import tech.maslov.rgenerator.domain.generator.usecase.all.*;
import tech.maslov.rgenerator.domain.generator.usecase.secured.*;
import tech.maslov.rgenerator.domain.templateResult.port.TemplateResultRepository;
import tech.maslov.rgenerator.domain.templateResult.service.ZipService;
import tech.maslov.rgenerator.domain.templateResult.usecase.all.TemplateResultDeleteUseCase;

public class GeneratorConfig {

    @RequiredArgsConstructor
    public static class Secured {
        protected final GeneratorRepository generatorRepository;
        protected final GeneratorIdGenerator idGenerator;
        protected final AuthorizationByTokenUseCase authorizationByTokenUseCase;

        public SecuredGeneratorCreateUseCase createUseCase() {
            return new SecuredGeneratorCreateUseCase(this.generatorRepository, idGenerator, this.authorizationByTokenUseCase);
        }

        public SecuredGeneratorDeleteUseCase deleteUseCase() {
            return new SecuredGeneratorDeleteUseCase(this.generatorRepository, this.authorizationByTokenUseCase);
        }

        public SecuredGeneratorUpdateUseCase updateUseCase() {
            return new SecuredGeneratorUpdateUseCase(this.generatorRepository, this.authorizationByTokenUseCase);
        }

        public SecuredGeneratorViewUseCase viewUseCase() {
            return new SecuredGeneratorViewUseCase(this.generatorRepository, this.authorizationByTokenUseCase);
        }
    }

    @RequiredArgsConstructor
    public static class All {
        protected final GeneratorRepository generatorRepository;
        protected final GeneratorIdGenerator generatorIdGenerator;
        protected final FileRepository fileRepository;
        protected final FileStorage fileStorage;
        protected final AuthorizationDevByTokenUseCase authorizationDevByTokenUseCase;
        protected final DeveloperRepository developerRepository;
        protected final UserRepository userRepository;
        protected final TemplateResultDeleteUseCase templateResultDeleteUseCase;

        public GeneratorCreateUseCase createUseCase() {
            return new GeneratorCreateUseCase(this.generatorRepository, generatorIdGenerator, authorizationDevByTokenUseCase, developerRepository, userRepository);
        }

        public GeneratorEditUseCase editUseCase() {
            return new GeneratorEditUseCase(this.generatorRepository, this.fileRepository, authorizationDevByTokenUseCase, developerRepository, userRepository);
        }

        public GeneratorViewUseCase viewUseCase() {
            return new GeneratorViewUseCase(this.generatorRepository, this.fileStorage, authorizationDevByTokenUseCase, developerRepository, userRepository, fileRepository);
        }

        public GeneratorDeleteUseCase deleteUseCase() {
            return new GeneratorDeleteUseCase(this.generatorRepository, this.fileStorage, authorizationDevByTokenUseCase, developerRepository, userRepository, fileRepository, templateResultDeleteUseCase);
        }

        public GeneratorGitHubUseCase generatorGitHubUseCase() {
            return new GeneratorGitHubUseCase(generatorRepository, authorizationDevByTokenUseCase, developerRepository, userRepository, fileStorage, fileRepository, editUseCase());
        }
    }

    protected final GeneratorRepository generatorRepository;
    protected final GeneratorIdGenerator idGenerator;
    protected final AuthorizationByTokenUseCase authorizationByTokenUseCase;
    protected final FileRepository fileRepository;
    protected final FileStorage fileStorage;
    protected final AuthorizationDevByTokenUseCase authorizationDevByTokenUseCase;
    protected final RefreshTokenRepository refreshTokenRepository;
    protected final AccessTokenStorage accessTokenStorage;
    protected final UserRepository userRepository;
    protected final DeveloperRepository developerRepository;
    protected final TemplateResultDeleteUseCase templateResultDeleteUseCase;
    protected final TemplateResultRepository templateResultRepository;
    protected final ZipService zipService;

    public final Secured secured;
    public final All all;

    public GeneratorConfig(GeneratorRepository generatorRepository, GeneratorIdGenerator idGenerator, AuthorizationByTokenUseCase authorizationByTokenUseCase, FileRepository fileRepository, FileStorage fileStorage, RefreshTokenRepository refreshTokenRepository, AccessTokenStorage accessTokenStorage, UserRepository userRepository, DeveloperRepository developerRepository, TemplateResultRepository templateResultRepository) {
        this.generatorRepository = generatorRepository;
        this.idGenerator = idGenerator;
        this.authorizationByTokenUseCase = authorizationByTokenUseCase;
        this.fileRepository = fileRepository;
        this.fileStorage = fileStorage;
        this.refreshTokenRepository = refreshTokenRepository;
        this.accessTokenStorage = accessTokenStorage;
        this.userRepository = userRepository;
        this.developerRepository = developerRepository;
        this.templateResultRepository = templateResultRepository;
        this.zipService = new ZipService(fileRepository, fileStorage);
        this.authorizationDevByTokenUseCase = new AuthorizationDevByTokenUseCase( refreshTokenRepository,  accessTokenStorage,  userRepository,  developerRepository);

        this.templateResultDeleteUseCase = new TemplateResultDeleteUseCase(
                generatorRepository, templateResultRepository,  zipService,  authorizationDevByTokenUseCase,  developerRepository, userRepository, fileStorage, fileRepository
        );

        this.secured = new Secured(this.generatorRepository, this.idGenerator, this.authorizationByTokenUseCase);
        this.all = new All(this.generatorRepository, idGenerator, fileRepository, fileStorage, authorizationDevByTokenUseCase, developerRepository, userRepository, templateResultDeleteUseCase);
    }

}