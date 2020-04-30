package tech.maslov.rgenerator.templateResult.usecase.admin;

import com.rcore.domain.base.usecase.AdminUseCase;
import com.rcore.domain.role.entity.Role;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.user.entity.UserEntity;
import tech.maslov.rgenerator.templateResult.port.TemplateResultRepository;

class TemplateResultAdminBaseUseCase extends AdminUseCase {

    protected final TemplateResultRepository templateResultRepository;

    public TemplateResultAdminBaseUseCase(UserEntity actor, TemplateResultRepository templateResultRepository, Role accessRole) throws AuthorizationException {
        super(actor, accessRole);
        this.templateResultRepository = templateResultRepository;
    }

}
