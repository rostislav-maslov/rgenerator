package tech.maslov.rgenerator.domain.generator.usecase.secured;

import tech.maslov.rgenerator.domain.generator.entity.GeneratorEntity;
import tech.maslov.rgenerator.domain.generator.port.GeneratorRepository;
import tech.maslov.rgenerator.domain.generator.access.GeneratorDeleteAccess;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.usecase.AuthorizationByTokenUseCase;

public class SecuredGeneratorDeleteUseCase extends SecuredGeneratorBaseUseCase {

    public SecuredGeneratorDeleteUseCase(GeneratorRepository generatorRepository, AuthorizationByTokenUseCase authorizationByTokenUseCase) {
        super(generatorRepository, new GeneratorDeleteAccess(), authorizationByTokenUseCase);
    }

    public Boolean delete(GeneratorEntity generatorEntity) throws AuthenticationException, AuthorizationException {
        checkAccess();

        return generatorRepository.delete(generatorEntity);
    }

    public Boolean deleteById(String id) throws AuthenticationException, AuthorizationException {
        checkAccess();

        return generatorRepository.deleteById(id);
    }

}