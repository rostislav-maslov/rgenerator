package tech.maslov.rgenerator.domain.generator.usecase.all;

import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.user.port.UserRepository;
import tech.maslov.rgenerator.domain.developer.port.DeveloperRepository;
import tech.maslov.rgenerator.domain.developer.usecase.all.AuthorizationDevByTokenUseCase;
import tech.maslov.rgenerator.domain.generator.entity.GeneratorEntity;
import tech.maslov.rgenerator.domain.generator.port.GeneratorIdGenerator;
import tech.maslov.rgenerator.domain.generator.port.GeneratorRepository;

public class GeneratorCreateUseCase extends GeneratorBaseUseCase {

    private final GeneratorIdGenerator generatorIdGenerator;

    public GeneratorCreateUseCase(GeneratorRepository generatorRepository, GeneratorIdGenerator generatorIdGenerator, AuthorizationDevByTokenUseCase authorizationDevByTokenUseCase, DeveloperRepository developerRepository, UserRepository userRepository) {
        super(generatorRepository, authorizationDevByTokenUseCase, developerRepository, userRepository);
        this.generatorIdGenerator = generatorIdGenerator;
    }

    public GeneratorEntity create(String title, String description, String example) throws AuthenticationException {
        GeneratorEntity generatorEntity = new GeneratorEntity();

        generatorEntity.setId(generatorIdGenerator.generate());
        generatorEntity.setTitle(title);
        generatorEntity.setDescription(description);
        generatorEntity.setExample(example);
        generatorEntity.setOwnerId(this.currentDeveloper().getId());

        return generatorRepository.save(generatorEntity);
    }
}
