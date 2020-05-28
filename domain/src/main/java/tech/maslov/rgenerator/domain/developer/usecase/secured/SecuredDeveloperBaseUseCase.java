package tech.maslov.rgenerator.domain.developer.usecase.secured;

import com.rcore.domain.access.entity.Access;
import com.rcore.domain.base.usecase.AdminUseCase;
import com.rcore.domain.token.usecase.AuthorizationByTokenUseCase;
import tech.maslov.rgenerator.domain.developer.port.DeveloperRepository;

class SecuredDeveloperBaseUseCase extends AdminUseCase {

protected final DeveloperRepository developerRepository;

    public SecuredDeveloperBaseUseCase(DeveloperRepository developerRepository, Access access, AuthorizationByTokenUseCase authorizationByTokenUseCase) {
        super(access, authorizationByTokenUseCase);
        this.developerRepository = developerRepository;
    }

}
