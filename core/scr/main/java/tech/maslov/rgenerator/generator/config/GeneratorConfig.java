package tech.maslov.rgenerator.generator.config;

import tech.maslov.rgenerator.generator.port.GeneratorIdGenerator;
import tech.maslov.rgenerator.generator.port.GeneratorRepository;
import tech.maslov.rgenerator.generator.usecase.admin.GeneratorCreateUseCase;
import tech.maslov.rgenerator.generator.usecase.admin.GeneratorDeleteUseCase;
import tech.maslov.rgenerator.generator.usecase.admin.GeneratorUpdateUseCase;
import tech.maslov.rgenerator.generator.usecase.admin.GeneratorViewUseCase;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.user.entity.UserEntity;

public class GeneratorConfig {

    public static class Admin {
        protected final GeneratorRepository generatorRepository;
        protected final GeneratorIdGenerator idGenerator;

        public Admin(GeneratorRepository generatorRepository, GeneratorIdGenerator idGenerator) {
            this.generatorRepository = generatorRepository;
            this.idGenerator = idGenerator;
        }

        public GeneratorCreateUseCase createUseCase(UserEntity actor) throws AuthorizationException {
            return new GeneratorCreateUseCase(actor, this.generatorRepository, idGenerator);
        }

        public GeneratorDeleteUseCase deleteUseCase(UserEntity actor) throws AuthorizationException {
            return new GeneratorDeleteUseCase(actor, this.generatorRepository);
        }

        public GeneratorUpdateUseCase updateUseCase(UserEntity actor) throws AuthorizationException {
            return new GeneratorUpdateUseCase(actor, this.generatorRepository);
        }

        public GeneratorViewUseCase viewUseCase(UserEntity actor) throws AuthorizationException {
            return new GeneratorViewUseCase(actor, this.generatorRepository);
        }
    }

    public static class All {

        public All() {
        }

    }

    private final GeneratorRepository generatorRepository;
    private final GeneratorIdGenerator idGenerator;

    public final Admin admin;
    public final All all;

    public GeneratorConfig(
            GeneratorRepository generatorRepository,
            GeneratorIdGenerator idGenerator
    ) {
        this.generatorRepository = generatorRepository;
        this.idGenerator = idGenerator;

        this.admin = new Admin(this.generatorRepository, this.idGenerator);
        this.all = new All();
    }

}
