package tech.maslov.rgenerator.domain.developer.usecase.all;

import com.rcore.domain.token.exception.AuthenticationException;
import tech.maslov.rgenerator.domain.developer.entity.DeveloperEntity;

import java.util.Optional;

public class MeUseCase {
    private final AuthorizationDevByTokenUseCase authorizationDevByTokenUseCase;

    public MeUseCase(AuthorizationDevByTokenUseCase authorizationDevByTokenUseCase) {
        this.authorizationDevByTokenUseCase = authorizationDevByTokenUseCase;
    }

    public DeveloperEntity me() throws AuthenticationException {
        Optional<DeveloperEntity> developerEntity = authorizationDevByTokenUseCase.currentDeveloper();
        return developerEntity.get();
    }
}
