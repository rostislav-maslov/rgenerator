package tech.maslov.rgenerator.domain.developer.usecase.secured;

import tech.maslov.rgenerator.domain.developer.entity.DeveloperEntity;
import tech.maslov.rgenerator.domain.developer.port.DeveloperIdGenerator;
import tech.maslov.rgenerator.domain.developer.port.DeveloperRepository;
import tech.maslov.rgenerator.domain.developer.access.DeveloperCreateAccess;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.usecase.AuthorizationByTokenUseCase;

public class SecuredDeveloperCreateUseCase extends SecuredDeveloperBaseUseCase {
    private final DeveloperIdGenerator idGenerator;

    public SecuredDeveloperCreateUseCase(DeveloperRepository developerRepository, DeveloperIdGenerator idGenerator, AuthorizationByTokenUseCase authorizationByTokenUseCase) {
        super(developerRepository, new DeveloperCreateAccess(), authorizationByTokenUseCase);
        this.idGenerator = idGenerator;
    }

    public DeveloperEntity create(DeveloperEntity developerEntity) throws AuthenticationException, AuthorizationException {
        checkAccess();

        developerEntity.setId(idGenerator.generate());

        return developerRepository.save(developerEntity);
    }

}