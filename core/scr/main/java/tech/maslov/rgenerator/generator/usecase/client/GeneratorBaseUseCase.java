package tech.maslov.rgenerator.generator.usecase.client;

import tech.maslov.rgenerator.generator.port.GeneratorRepository;

class GeneratorBaseUseCase{

    protected final GeneratorRepository generatorRepository;

    public GeneratorBaseUseCase(GeneratorRepository generatorRepository) {
        this.generatorRepository = generatorRepository;
    }

}
