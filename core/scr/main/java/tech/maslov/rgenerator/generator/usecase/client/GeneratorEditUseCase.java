package tech.maslov.rgenerator.generator.usecase.client;

import tech.maslov.rgenerator.generator.entity.GeneratorEntity;
import tech.maslov.rgenerator.generator.port.GeneratorRepository;

public class GeneratorEditUseCase extends GeneratorBaseUseCase {
    public GeneratorEditUseCase(GeneratorRepository generatorRepository) {
        super(generatorRepository);
    }

    public GeneratorEntity editInfo(String id, String title, String description) {
        GeneratorEntity generator = generatorRepository.findById(id).get();

        generator.setTitle(title);
        generator.setDescription(description);

        return generatorRepository.save(generator);
    }

    public GeneratorEntity editJson(String id, String example) {
        GeneratorEntity generator = generatorRepository.findById(id).get();
        generator.setExample(example);

        return generatorRepository.save(generator);
    }
}
