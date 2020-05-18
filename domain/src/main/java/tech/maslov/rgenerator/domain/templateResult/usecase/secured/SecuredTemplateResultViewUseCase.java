package tech.maslov.rgenerator.domain.templateResult.usecase.secured;

import tech.maslov.rgenerator.domain.templateResult.entity.TemplateResultEntity;
import tech.maslov.rgenerator.domain.templateResult.port.TemplateResultRepository;
import tech.maslov.rgenerator.domain.templateResult.access.TemplateResultViewAccess;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.usecase.AuthorizationByTokenUseCase;
import com.rcore.domain.base.port.SearchRequest;
import com.rcore.domain.base.port.SearchResult;

import java.util.Optional;

public class SecuredTemplateResultViewUseCase extends SecuredTemplateResultBaseUseCase {

    public SecuredTemplateResultViewUseCase(TemplateResultRepository templateResultRepository, AuthorizationByTokenUseCase authorizationByTokenUseCase) {
        super(templateResultRepository, new TemplateResultViewAccess(), authorizationByTokenUseCase);
    }

    public Optional<TemplateResultEntity> findById(String id) throws AuthenticationException, AuthorizationException {
        checkAccess();
        return templateResultRepository.findById(id);
    }

    public SearchResult<TemplateResultEntity> search(SearchRequest request) throws AuthenticationException, AuthorizationException {
        checkAccess();
        return templateResultRepository.find(request);
    }

}