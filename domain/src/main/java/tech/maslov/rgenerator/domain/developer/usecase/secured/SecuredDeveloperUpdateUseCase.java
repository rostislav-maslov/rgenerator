package tech.maslov.rgenerator.domain.developer.usecase.secured;

import tech.maslov.rgenerator.domain.developer.entity.DeveloperEntity;
import tech.maslov.rgenerator.domain.developer.port.DeveloperRepository;
import tech.maslov.rgenerator.domain.developer.access.DeveloperUpdateAccess;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.usecase.AuthorizationByTokenUseCase;

import java.time.LocalDateTime;

public class SecuredDeveloperUpdateUseCase extends SecuredDeveloperBaseUseCase {

    public SecuredDeveloperUpdateUseCase(DeveloperRepository developerRepository, AuthorizationByTokenUseCase authorizationByTokenUseCase) {
        super(developerRepository, new DeveloperUpdateAccess(), authorizationByTokenUseCase);
    }

    public DeveloperEntity update(DeveloperEntity developerEntity) throws AuthenticationException, AuthorizationException {
        checkAccess();

        developerEntity.setUpdatedAt(LocalDateTime.now());

        return developerRepository.save(developerEntity);
    }

}