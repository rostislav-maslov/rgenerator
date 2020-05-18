package tech.maslov.rgenerator.domain.generator.usecase.secured;

import tech.maslov.rgenerator.domain.generator.entity.GeneratorEntity;
import tech.maslov.rgenerator.domain.generator.port.GeneratorRepository;
import tech.maslov.rgenerator.domain.generator.access.GeneratorUpdateAccess;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.usecase.AuthorizationByTokenUseCase;

import java.time.LocalDateTime;

public class SecuredGeneratorUpdateUseCase extends SecuredGeneratorBaseUseCase {

    public SecuredGeneratorUpdateUseCase(GeneratorRepository generatorRepository, AuthorizationByTokenUseCase authorizationByTokenUseCase) {
        super(generatorRepository, new GeneratorUpdateAccess(), authorizationByTokenUseCase);
    }

    public GeneratorEntity update(GeneratorEntity generatorEntity) throws AuthenticationException, AuthorizationException {
        checkAccess();

        generatorEntity.setUpdatedAt(LocalDateTime.now());

        return generatorRepository.save(generatorEntity);
    }

}