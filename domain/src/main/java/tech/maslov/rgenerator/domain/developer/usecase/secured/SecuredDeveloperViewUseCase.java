package tech.maslov.rgenerator.domain.developer.usecase.secured;

import tech.maslov.rgenerator.domain.developer.entity.DeveloperEntity;
import tech.maslov.rgenerator.domain.developer.port.DeveloperRepository;
import tech.maslov.rgenerator.domain.developer.access.DeveloperViewAccess;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.usecase.AuthorizationByTokenUseCase;
import com.rcore.domain.base.port.SearchRequest;
import com.rcore.domain.base.port.SearchResult;

import java.util.Optional;

public class SecuredDeveloperViewUseCase extends SecuredDeveloperBaseUseCase {

    public SecuredDeveloperViewUseCase(DeveloperRepository developerRepository, AuthorizationByTokenUseCase authorizationByTokenUseCase) {
        super(developerRepository, new DeveloperViewAccess(), authorizationByTokenUseCase);
    }

    public Optional<DeveloperEntity> findById(String id) throws AuthenticationException, AuthorizationException {
        checkAccess();
        return developerRepository.findById(id);
    }

    public SearchResult<DeveloperEntity> search(SearchRequest request) throws AuthenticationException, AuthorizationException {
        checkAccess();
        return developerRepository.find(request);
    }

}