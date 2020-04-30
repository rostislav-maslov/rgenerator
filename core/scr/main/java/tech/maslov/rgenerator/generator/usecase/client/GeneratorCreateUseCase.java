package tech.maslov.rgenerator.generator.usecase.client;

import tech.maslov.rgenerator.generator.entity.GeneratorEntity;
import tech.maslov.rgenerator.generator.port.GeneratorRepository;

public class GeneratorCreateUseCase extends GeneratorBaseUseCase {
    public GeneratorCreateUseCase(GeneratorRepository generatorRepository) {
        super(generatorRepository);
    }

    public GeneratorEntity create(String title, String description, String example) {
        GeneratorEntity generator = new GeneratorEntity();

        generator.setTitle(title);
        generator.setDescription(description);
        generator.setExample(example);

        return this.generatorRepository.save(generator);
    }
}
