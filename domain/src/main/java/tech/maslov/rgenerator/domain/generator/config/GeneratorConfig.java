package tech.maslov.rgenerator.domain.generator.config;

import com.rcore.domain.token.usecase.AuthorizationByTokenUseCase;
import tech.maslov.rgenerator.domain.generator.port.GeneratorRepository;
import tech.maslov.rgenerator.domain.generator.port.GeneratorIdGenerator;
import lombok.RequiredArgsConstructor;
import tech.maslov.rgenerator.domain.generator.usecase.secured.*;

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
    }

    protected final GeneratorRepository generatorRepository;
    protected final GeneratorIdGenerator idGenerator;
    protected final AuthorizationByTokenUseCase authorizationByTokenUseCase;

    public final Secured secured;
    public final All all;

    public GeneratorConfig(GeneratorRepository generatorRepository, GeneratorIdGenerator idGenerator, AuthorizationByTokenUseCase authorizationByTokenUseCase) {
        this.generatorRepository = generatorRepository;
        this.idGenerator = idGenerator;
        this.authorizationByTokenUseCase = authorizationByTokenUseCase;

        this.secured = new Secured(this.generatorRepository, this.idGenerator, this.authorizationByTokenUseCase);
        this.all = new All(this.generatorRepository);
    }

}