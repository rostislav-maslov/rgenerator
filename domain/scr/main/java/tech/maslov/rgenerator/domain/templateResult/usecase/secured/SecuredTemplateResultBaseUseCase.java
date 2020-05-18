package tech.maslov.rgenerator.domain.templateResult.usecase.secured;

import com.rcore.domain.access.entity.Access;
import com.rcore.domain.base.usecase.AdminUseCase;
import com.rcore.domain.token.usecase.AuthorizationByTokenUseCase;
import tech.maslov.rgenerator.domain.templateResult.port.TemplateResultRepository;

class SecuredTemplateResultBaseUseCase extends AdminUseCase {

protected final TemplateResultRepository templateResultRepository;

    public SecuredTemplateResultBaseUseCase(TemplateResultRepository templateResultRepository, Access access, AuthorizationByTokenUseCase authorizationByTokenUseCase) {
        super(access, authorizationByTokenUseCase);
        this.templateResultRepository = templateResultRepository;
    }

}
