package tech.maslov.rgenerator.generator.usecase.admin;

import tech.maslov.rgenerator.generator.entity.GeneratorEntity;
import tech.maslov.rgenerator.generator.port.GeneratorIdGenerator;
import tech.maslov.rgenerator.generator.port.GeneratorRepository;
import tech.maslov.rgenerator.generator.role.AdminGeneratorCreateAccess;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.user.entity.UserEntity;


public class GeneratorCreateUseCase  extends GeneratorAdminBaseUseCase {
    private final GeneratorIdGenerator idGenerator;

    public GeneratorCreateUseCase(UserEntity actor, GeneratorRepository generatorRepository, GeneratorIdGenerator idGenerator) throws AuthorizationException {
        super(actor, generatorRepository, new AdminGeneratorCreateAccess());
        this.idGenerator = idGenerator;
    }

    public GeneratorEntity create(GeneratorEntity generatorEntity) {
        generatorEntity.setId(idGenerator.generate());

        generatorEntity = generatorRepository.save(generatorEntity);

        return generatorEntity;
    }


}