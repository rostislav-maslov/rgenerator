package tech.maslov.rgenerator.domain.developer.usecase.all;

import com.rcore.domain.token.entity.AccessTokenEntity;
import com.rcore.domain.token.entity.TokenPair;
import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.exception.RefreshTokenCreationException;
import com.rcore.domain.user.entity.UserEntity;
import com.rcore.domain.user.exception.UserAlreadyExistException;
import com.rcore.domain.user.exception.UserBlockedException;
import com.rcore.domain.user.exception.UserNotFoundException;
import com.rcore.domain.user.port.PasswordGenerator;
import com.rcore.domain.user.port.UserIdGenerator;
import com.rcore.domain.user.port.UserRepository;
import com.rcore.domain.user.usecase.all.EmailAuthenticationUseCase;
import tech.maslov.rgenerator.domain.developer.entity.DeveloperEntity;
import tech.maslov.rgenerator.domain.developer.port.DeveloperRepository;

import java.util.Optional;

public class SignUpUseCase {
    private final UserRepository userRepository;
    private final PasswordGenerator passwordGenerator;
    private final DeveloperRepository developerRepository;
    private final UserIdGenerator userIdGenerator;
    private final EmailAuthenticationUseCase emailAuthenticationUseCase;

    public SignUpUseCase(UserRepository userRepository, PasswordGenerator passwordGenerator, DeveloperRepository developerRepository, UserIdGenerator userIdGenerator, EmailAuthenticationUseCase emailAuthenticationUseCase) {
        this.userRepository = userRepository;
        this.passwordGenerator = passwordGenerator;
        this.developerRepository = developerRepository;
        this.userIdGenerator = userIdGenerator;
        this.emailAuthenticationUseCase = emailAuthenticationUseCase;
    }

    private DeveloperEntity create(UserEntity userEntity) {
        DeveloperEntity developerEntity = new DeveloperEntity();
        developerEntity.setId(userEntity.getId());
        return developerRepository.save(developerEntity);
    }

    private UserEntity createUser(String login, String email, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userIdGenerator.generate());
        userEntity.setEmail(email);
        userEntity.setLogin(login);
        userEntity.setPassword(passwordGenerator.generate(userEntity.getId(), password));
        return userRepository.save(userEntity);
    }

    private DeveloperEntity create(String login, String email, String password) {
        UserEntity userEntity = createUser(login, email, password);
        return create(userEntity);
    }

    public TokenPair signUp(String login, String email, String password) throws UserAlreadyExistException, AuthenticationException, RefreshTokenCreationException, UserNotFoundException, UserBlockedException {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if (userEntity.isEmpty()) {
            userEntity = userRepository.findByLogin(login);
        }

        if (userEntity.isPresent()) {
            UserEntity ue = userEntity.get();
            if (passwordGenerator.check(ue.getId(), password, ue.getPassword()) == false) {
                throw new UserAlreadyExistException();
            }
            Optional<DeveloperEntity> developerEntity = developerRepository.findById(ue.getId());
            if (developerEntity.isPresent()) {
                return emailAuthenticationUseCase.authentication(email, password);
            }

            Optional<DeveloperEntity> de = developerRepository.findById(ue.getId());
            if(de.isEmpty()) {
                create(ue);
            }
            return emailAuthenticationUseCase.authentication(email, password);
        }

        DeveloperEntity developerEntity = create(login, email, password);
        return emailAuthenticationUseCase.authentication(email, password);
    }
}
