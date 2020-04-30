package tech.maslov.rgenerator.generator.usecase.admin;

import tech.maslov.rgenerator.generator.entity.GeneratorEntity;
import tech.maslov.rgenerator.generator.port.GeneratorRepository;
import tech.maslov.rgenerator.generator.role.AdminGeneratorViewAccess;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.user.entity.UserEntity;
import com.rcore.domain.base.port.SearchResult;

public class GeneratorViewUseCase extends GeneratorAdminBaseUseCase {

    public GeneratorViewUseCase(UserEntity actor, GeneratorRepository generatorRepository) throws AuthorizationException {
        super(actor, generatorRepository, new AdminGeneratorViewAccess());
    }

    public GeneratorEntity findById(String id) {
        return generatorRepository.findById(id);
    }

    public GeneratorEntity search(String id) {
        return generatorRepository.findById(id);
    }

    public SearchResult<GeneratorEntity> find(Long size, Long skip) {
        return generatorRepository.find(size, skip);
    }

}