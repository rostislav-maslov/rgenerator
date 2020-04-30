package tech.maslov.rgenerator.generator.usecase.admin;

import tech.maslov.rgenerator.generator.entity.GeneratorEntity;
import tech.maslov.rgenerator.generator.port.GeneratorRepository;
import tech.maslov.rgenerator.generator.role.AdminGeneratorUpdateAccess;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.user.entity.UserEntity;

public class GeneratorUpdateUseCase  extends GeneratorAdminBaseUseCase {

    public GeneratorUpdateUseCase(UserEntity actor, GeneratorRepository generatorRepository)throws AuthorizationException {
        super(actor, generatorRepository, new AdminGeneratorUpdateAccess());
    }

    public GeneratorEntity update(GeneratorEntity generatorEntity){
        generatorEntity.setUpdatedAt(new Date());
        return generatorRepository.save(generatorEntity);
    }


}