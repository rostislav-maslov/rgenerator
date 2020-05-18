package tech.maslov.rgenerator.domain.templateResult.usecase.secured;

import tech.maslov.rgenerator.domain.templateResult.entity.TemplateResultEntity;
import tech.maslov.rgenerator.domain.templateResult.port.TemplateResultRepository;
import tech.maslov.rgenerator.domain.templateResult.access.TemplateResultDeleteAccess;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.usecase.AuthorizationByTokenUseCase;

public class SecuredTemplateResultDeleteUseCase extends SecuredTemplateResultBaseUseCase {

    public SecuredTemplateResultDeleteUseCase(TemplateResultRepository templateResultRepository, AuthorizationByTokenUseCase authorizationByTokenUseCase) {
        super(templateResultRepository, new TemplateResultDeleteAccess(), authorizationByTokenUseCase);
    }

    public Boolean delete(TemplateResultEntity templateResultEntity) throws AuthenticationException, AuthorizationException {
        checkAccess();

        return templateResultRepository.delete(templateResultEntity);
    }

    public Boolean deleteById(String id) throws AuthenticationException, AuthorizationException {
        checkAccess();

        return templateResultRepository.deleteById(id);
    }

}