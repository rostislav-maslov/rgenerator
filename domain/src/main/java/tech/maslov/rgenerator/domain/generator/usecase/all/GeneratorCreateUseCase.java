package tech.maslov.rgenerator.domain.generator.usecase.all;

import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.exception.AuthorizationException;
import tech.maslov.rgenerator.domain.generator.entity.GeneratorEntity;
import tech.maslov.rgenerator.domain.generator.port.GeneratorIdGenerator;
import tech.maslov.rgenerator.domain.generator.port.GeneratorRepository;

public class GeneratorCreateUseCase extends GeneratorBaseUseCase {

    private final GeneratorIdGenerator generatorIdGenerator;

    public GeneratorCreateUseCase(GeneratorRepository generatorRepository, GeneratorIdGenerator generatorIdGenerator) {
        super(generatorRepository);
        this.generatorIdGenerator = generatorIdGenerator;
    }

    public GeneratorEntity create(String title, String description, String example) throws AuthenticationException, AuthorizationException {
        GeneratorEntity generatorEntity = new GeneratorEntity();

        generatorEntity.setId(generatorIdGenerator.generate());
        generatorEntity.setTitle(title);
        generatorEntity.setDescription(description);
        generatorEntity.setExample(example);

        return generatorRepository.save(generatorEntity);
    }
}
