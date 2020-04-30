package tech.maslov.rgenerator.generator.usecase.admin;

import com.rcore.domain.base.usecase.AdminUseCase;
import com.rcore.domain.role.entity.Role;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.user.entity.UserEntity;
import tech.maslov.rgenerator.generator.port.GeneratorRepository;

class GeneratorAdminBaseUseCase extends AdminUseCase {

    protected final GeneratorRepository generatorRepository;

    public GeneratorAdminBaseUseCase(UserEntity actor, GeneratorRepository generatorRepository, Role accessRole) throws AuthorizationException {
        super(actor, accessRole);
        this.generatorRepository = generatorRepository;
    }

}
