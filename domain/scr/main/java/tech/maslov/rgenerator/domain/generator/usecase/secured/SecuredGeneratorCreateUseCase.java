package tech.maslov.rgenerator.domain.generator.usecase.secured;

import tech.maslov.rgenerator.domain.generator.entity.GeneratorEntity;
import tech.maslov.rgenerator.domain.generator.port.GeneratorIdGenerator;
import tech.maslov.rgenerator.domain.generator.port.GeneratorRepository;
import tech.maslov.rgenerator.domain.generator.access.GeneratorCreateAccess;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.usecase.AuthorizationByTokenUseCase;

public class SecuredGeneratorCreateUseCase extends SecuredGeneratorBaseUseCase {
    private final GeneratorIdGenerator idGenerator;

    public SecuredGeneratorCreateUseCase(GeneratorRepository generatorRepository, GeneratorIdGenerator idGenerator, AuthorizationByTokenUseCase authorizationByTokenUseCase) {
        super(generatorRepository, new GeneratorCreateAccess(), authorizationByTokenUseCase);
        this.idGenerator = idGenerator;
    }

    public GeneratorEntity create(GeneratorEntity generatorEntity) throws AuthenticationException, AuthorizationException {
        checkAccess();

        generatorEntity.setId(idGenerator.generate());

        return generatorRepository.save(generatorEntity);
    }

}