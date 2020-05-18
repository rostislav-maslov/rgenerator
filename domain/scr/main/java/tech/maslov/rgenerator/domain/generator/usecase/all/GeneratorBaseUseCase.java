package tech.maslov.rgenerator.domain.generator.usecase.all;

import tech.maslov.rgenerator.domain.generator.port.GeneratorRepository;


class GeneratorBaseUseCase {

    protected final GeneratorRepository generatorRepository;

    public GeneratorBaseUseCase(GeneratorRepository generatorRepository) {
        this.generatorRepository = generatorRepository;
    }

}
