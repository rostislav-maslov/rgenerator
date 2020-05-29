package tech.maslov.rgenerator.domain.developer.usecase.sugar;

import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.user.entity.UserEntity;
import com.rcore.domain.user.port.UserRepository;
import tech.maslov.rgenerator.domain.developer.entity.DeveloperEntity;
import tech.maslov.rgenerator.domain.developer.port.DeveloperRepository;
import tech.maslov.rgenerator.domain.developer.usecase.all.AuthorizationDevByTokenUseCase;
import tech.maslov.rgenerator.domain.generator.entity.GeneratorEntity;

import java.util.Optional;

public abstract class DeveloperSugarUseCase {
    protected final AuthorizationDevByTokenUseCase authorizationDevByTokenUseCase;
    protected final UserRepository userRepository;
    protected final DeveloperRepository developerRepository;

    public DeveloperSugarUseCase(AuthorizationDevByTokenUseCase authorizationDevByTokenUseCase, UserRepository userRepository, DeveloperRepository developerRepository) {
        this.authorizationDevByTokenUseCase = authorizationDevByTokenUseCase;
        this.userRepository = userRepository;
        this.developerRepository = developerRepository;
    }

    protected DeveloperEntity currentDeveloper() throws AuthenticationException {
        Optional<DeveloperEntity> developerEntityOptional = authorizationDevByTokenUseCase.currentDeveloper();
        if (developerEntityOptional.isEmpty()) throw new AuthenticationException();

        return developerEntityOptional.get();
    }

    protected UserEntity currentEntity() throws AuthenticationException {
        Optional<DeveloperEntity> developerEntityOptional = authorizationDevByTokenUseCase.currentDeveloper();
        if (developerEntityOptional.isEmpty()) throw new AuthenticationException();

        Optional<UserEntity> userEntity = userRepository.findById(developerEntityOptional.get().getId());
        if (userEntity.isEmpty()) throw new AuthenticationException();

        return userEntity.get();
    }

    protected DeveloperEntity owner(GeneratorEntity generatorEntity) {
        return developerRepository.findById(generatorEntity.getOwnerId()).get();
    }

    protected Boolean checkEditAccess(GeneratorEntity generatorEntity) throws AuthenticationException, AuthorizationException {
        DeveloperEntity current = currentDeveloper();
        if(generatorEntity.getOwnerId().equals(current.getId())) return true;

        throw new AuthorizationException();
    }

    protected Boolean checkViewAccess(GeneratorEntity generatorEntity) throws AuthenticationException, AuthorizationException {
        return true;
    }

}
