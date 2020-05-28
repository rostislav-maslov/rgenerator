package tech.maslov.rgenerator.domain.developer.usecase.secured;

import tech.maslov.rgenerator.domain.developer.entity.DeveloperEntity;
import tech.maslov.rgenerator.domain.developer.port.DeveloperRepository;
import tech.maslov.rgenerator.domain.developer.access.DeveloperDeleteAccess;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.usecase.AuthorizationByTokenUseCase;

public class SecuredDeveloperDeleteUseCase extends SecuredDeveloperBaseUseCase {

    public SecuredDeveloperDeleteUseCase(DeveloperRepository developerRepository, AuthorizationByTokenUseCase authorizationByTokenUseCase) {
        super(developerRepository, new DeveloperDeleteAccess(), authorizationByTokenUseCase);
    }

    public Boolean delete(DeveloperEntity developerEntity) throws AuthenticationException, AuthorizationException {
        checkAccess();

        return developerRepository.delete(developerEntity);
    }

}