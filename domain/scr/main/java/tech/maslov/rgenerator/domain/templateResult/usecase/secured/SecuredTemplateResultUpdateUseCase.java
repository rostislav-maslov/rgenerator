package tech.maslov.rgenerator.domain.templateResult.usecase.secured;

import tech.maslov.rgenerator.domain.templateResult.entity.TemplateResultEntity;
import tech.maslov.rgenerator.domain.templateResult.port.TemplateResultRepository;
import tech.maslov.rgenerator.domain.templateResult.access.TemplateResultUpdateAccess;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.usecase.AuthorizationByTokenUseCase;

import java.time.LocalDateTime;

public class SecuredTemplateResultUpdateUseCase extends SecuredTemplateResultBaseUseCase {

    public SecuredTemplateResultUpdateUseCase(TemplateResultRepository templateResultRepository, AuthorizationByTokenUseCase authorizationByTokenUseCase) {
        super(templateResultRepository, new TemplateResultUpdateAccess(), authorizationByTokenUseCase);
    }

    public TemplateResultEntity update(TemplateResultEntity templateResultEntity) throws AuthenticationException, AuthorizationException {
        checkAccess();

        templateResultEntity.setUpdatedAt(LocalDateTime.now());

        return templateResultRepository.save(templateResultEntity);
    }

}