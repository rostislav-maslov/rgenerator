package tech.maslov.rgenerator.generator.usecase.admin;

import tech.maslov.rgenerator.generator.entity.GeneratorEntity;
import tech.maslov.rgenerator.generator.port.GeneratorRepository;
import tech.maslov.rgenerator.generator.role.AdminGeneratorDeleteAccess;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.user.entity.UserEntity;

public class GeneratorDeleteUseCase  extends GeneratorAdminBaseUseCase {

    public GeneratorDeleteUseCase(UserEntity actor, GeneratorRepository generatorRepository) throws AuthorizationException {
        super(actor, generatorRepository, new AdminGeneratorDeleteAccess());
    }

    public Boolean delete(GeneratorEntity generatorEntity){
        generatorRepository.delete(generatorEntity);

        return true;
    }


}