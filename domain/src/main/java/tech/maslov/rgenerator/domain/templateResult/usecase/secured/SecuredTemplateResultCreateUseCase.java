package tech.maslov.rgenerator.domain.templateResult.usecase.secured;

import tech.maslov.rgenerator.domain.templateResult.entity.TemplateResultEntity;
import tech.maslov.rgenerator.domain.templateResult.port.TemplateResultIdGenerator;
import tech.maslov.rgenerator.domain.templateResult.port.TemplateResultRepository;
import tech.maslov.rgenerator.domain.templateResult.access.TemplateResultCreateAccess;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.usecase.AuthorizationByTokenUseCase;

public class SecuredTemplateResultCreateUseCase extends SecuredTemplateResultBaseUseCase {
    private final TemplateResultIdGenerator idGenerator;

    public SecuredTemplateResultCreateUseCase(TemplateResultRepository templateResultRepository, TemplateResultIdGenerator idGenerator, AuthorizationByTokenUseCase authorizationByTokenUseCase) {
        super(templateResultRepository, new TemplateResultCreateAccess(), authorizationByTokenUseCase);
        this.idGenerator = idGenerator;
    }

    public TemplateResultEntity create(TemplateResultEntity templateResultEntity) throws AuthenticationException, AuthorizationException {
        checkAccess();

        templateResultEntity.setId(idGenerator.generate());

        return templateResultRepository.save(templateResultEntity);
    }

}