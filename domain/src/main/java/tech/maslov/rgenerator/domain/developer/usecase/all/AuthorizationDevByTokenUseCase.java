package tech.maslov.rgenerator.domain.developer.usecase.all;

import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.port.AccessTokenStorage;
import com.rcore.domain.token.port.RefreshTokenRepository;
import com.rcore.domain.token.usecase.AuthorizationByTokenUseCase;
import com.rcore.domain.user.entity.UserEntity;
import com.rcore.domain.user.port.UserRepository;
import tech.maslov.rgenerator.domain.developer.entity.DeveloperEntity;
import tech.maslov.rgenerator.domain.developer.port.DeveloperRepository;

import java.util.Optional;

public class AuthorizationDevByTokenUseCase extends AuthorizationByTokenUseCase {
    private final DeveloperRepository developerRepository;

    public AuthorizationDevByTokenUseCase(RefreshTokenRepository refreshTokenRepository, AccessTokenStorage accessTokenStorage, UserRepository userRepository, DeveloperRepository developerRepository) {
        super(refreshTokenRepository, accessTokenStorage, userRepository);
        this.developerRepository = developerRepository;
    }

    public Optional<DeveloperEntity> currentDeveloper() throws AuthenticationException {
        UserEntity userEntity = currentUser();
        return developerRepository.findById(userEntity.getId());
    }
}
