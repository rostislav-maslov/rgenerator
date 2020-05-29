package tech.maslov.rgenerator.domain.generator.usecase.all;

import com.rcore.domain.user.port.UserRepository;
import tech.maslov.rgenerator.domain.developer.port.DeveloperRepository;
import tech.maslov.rgenerator.domain.developer.usecase.all.AuthorizationDevByTokenUseCase;
import tech.maslov.rgenerator.domain.developer.usecase.sugar.DeveloperSugarUseCase;
import tech.maslov.rgenerator.domain.generator.port.GeneratorRepository;


class GeneratorBaseUseCase extends DeveloperSugarUseCase {

    protected final GeneratorRepository generatorRepository;


    public GeneratorBaseUseCase(GeneratorRepository generatorRepository, AuthorizationDevByTokenUseCase authorizationDevByTokenUseCase, DeveloperRepository developerRepository, UserRepository userRepository) {
        super(authorizationDevByTokenUseCase, userRepository, developerRepository);
        this.generatorRepository = generatorRepository;
    }


}
