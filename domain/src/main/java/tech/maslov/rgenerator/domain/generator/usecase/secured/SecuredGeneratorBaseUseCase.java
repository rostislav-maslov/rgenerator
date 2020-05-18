package tech.maslov.rgenerator.domain.generator.usecase.secured;

import com.rcore.domain.access.entity.Access;
import com.rcore.domain.base.usecase.AdminUseCase;
import com.rcore.domain.token.usecase.AuthorizationByTokenUseCase;
import tech.maslov.rgenerator.domain.generator.port.GeneratorRepository;

class SecuredGeneratorBaseUseCase extends AdminUseCase {

protected final GeneratorRepository generatorRepository;

    public SecuredGeneratorBaseUseCase(GeneratorRepository generatorRepository, Access access, AuthorizationByTokenUseCase authorizationByTokenUseCase) {
        super(access, authorizationByTokenUseCase);
        this.generatorRepository = generatorRepository;
    }

}
