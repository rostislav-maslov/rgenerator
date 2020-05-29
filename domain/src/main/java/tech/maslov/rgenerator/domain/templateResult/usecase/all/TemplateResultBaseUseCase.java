package tech.maslov.rgenerator.domain.templateResult.usecase.all;

import com.rcore.domain.user.port.UserRepository;
import tech.maslov.rgenerator.domain.developer.port.DeveloperRepository;
import tech.maslov.rgenerator.domain.developer.usecase.all.AuthorizationDevByTokenUseCase;
import tech.maslov.rgenerator.domain.developer.usecase.sugar.DeveloperSugarUseCase;
import tech.maslov.rgenerator.domain.templateResult.port.TemplateResultRepository;


class TemplateResultBaseUseCase extends DeveloperSugarUseCase {

    protected final TemplateResultRepository templateResultRepository;

    public TemplateResultBaseUseCase(TemplateResultRepository templateResultRepository, AuthorizationDevByTokenUseCase authorizationDevByTokenUseCase, DeveloperRepository developerRepository, UserRepository userRepository) {
        super(authorizationDevByTokenUseCase, userRepository, developerRepository);
        this.templateResultRepository = templateResultRepository;
    }

}
