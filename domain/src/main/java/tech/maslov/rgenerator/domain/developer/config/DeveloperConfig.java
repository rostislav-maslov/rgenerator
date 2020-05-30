package tech.maslov.rgenerator.domain.developer.config;

import com.rcore.domain.token.port.*;
import com.rcore.domain.token.usecase.AuthorizationByTokenUseCase;
import com.rcore.domain.token.usecase.CreateAccessTokenUseCase;
import com.rcore.domain.token.usecase.CreateRefreshTokenUseCase;
import com.rcore.domain.user.port.PasswordGenerator;
import com.rcore.domain.user.port.UserIdGenerator;
import com.rcore.domain.user.port.UserRepository;
import com.rcore.domain.user.usecase.all.EmailAuthenticationUseCase;
import tech.maslov.rgenerator.domain.developer.port.DeveloperRepository;
import tech.maslov.rgenerator.domain.developer.port.DeveloperIdGenerator;
import lombok.RequiredArgsConstructor;
import tech.maslov.rgenerator.domain.developer.usecase.all.AuthorizationDevByTokenUseCase;
import tech.maslov.rgenerator.domain.developer.usecase.all.GitHubConnectUseCase;
import tech.maslov.rgenerator.domain.developer.usecase.all.MeUseCase;
import tech.maslov.rgenerator.domain.developer.usecase.all.SignUpUseCase;
import tech.maslov.rgenerator.domain.developer.usecase.secured.*;

public class DeveloperConfig {

    @RequiredArgsConstructor
    public static class Secured {
        protected final DeveloperRepository developerRepository;
        protected final DeveloperIdGenerator idGenerator;
        protected final AuthorizationByTokenUseCase authorizationByTokenUseCase;

        public SecuredDeveloperCreateUseCase createUseCase() {
            return new SecuredDeveloperCreateUseCase(this.developerRepository, idGenerator, this.authorizationByTokenUseCase);
        }

        public SecuredDeveloperDeleteUseCase deleteUseCase() {
            return new SecuredDeveloperDeleteUseCase(this.developerRepository, this.authorizationByTokenUseCase);
        }

        public SecuredDeveloperUpdateUseCase updateUseCase() {
            return new SecuredDeveloperUpdateUseCase(this.developerRepository, this.authorizationByTokenUseCase);
        }

        public SecuredDeveloperViewUseCase viewUseCase() {
            return new SecuredDeveloperViewUseCase(this.developerRepository, this.authorizationByTokenUseCase);
        }
    }

    @RequiredArgsConstructor
    public static class All {
        protected final String gitGubClientId;
        protected final DeveloperRepository developerRepository;
        protected final RefreshTokenRepository refreshTokenRepository;
        protected final AccessTokenStorage accessTokenStorage;
        protected final UserRepository userRepository;
        protected final CreateAccessTokenUseCase createAccessTokenUseCase;
        protected final UserIdGenerator userIdGenerator;
        protected final PasswordGenerator passwordGenerator;
        protected final EmailAuthenticationUseCase emailAuthenticationUseCase;
        protected final String gitGubClientSecret;

        public AuthorizationDevByTokenUseCase authorizationDevByTokenUseCase() {
            return new AuthorizationDevByTokenUseCase(refreshTokenRepository, accessTokenStorage, userRepository, developerRepository);
        }

        public MeUseCase meUseCase() {
            return new MeUseCase(this.authorizationDevByTokenUseCase());
        }

        public SignUpUseCase signUpUseCase() {
            return new SignUpUseCase(userRepository, passwordGenerator, developerRepository, userIdGenerator, emailAuthenticationUseCase);
        }

        public EmailAuthenticationUseCase loginUseCase() {
            return emailAuthenticationUseCase;
        }

        public GitHubConnectUseCase gitHubConnectUseCase() {
            return new GitHubConnectUseCase(authorizationDevByTokenUseCase(), developerRepository, gitGubClientSecret, gitGubClientId );
        }
    }

    protected final DeveloperRepository developerRepository;
    protected final DeveloperIdGenerator idGenerator;
    protected final AuthorizationByTokenUseCase authorizationByTokenUseCase;
    protected final RefreshTokenRepository refreshTokenRepository;
    protected final AccessTokenStorage accessTokenStorage;
    protected final UserRepository userRepository;
    protected final CreateAccessTokenUseCase createAccessTokenUseCase;
    protected final EmailAuthenticationUseCase emailAuthenticationUseCase;
    protected final UserIdGenerator userIdGenerator;
    protected final PasswordGenerator passwordGenerator;
    protected final AccessTokenIdGenerator accessTokenIdGenerator;
    protected final CreateRefreshTokenUseCase createRefreshTokenUseCase;
    protected final RefreshTokenIdGenerator refreshTokenIdGenerator;
    protected final TokenSaltGenerator tokenSaltGenerator;
    protected final String gitGubClientSecret;
    protected final String gitGubClientId;

    public final Secured secured;
    public final All all;


    public DeveloperConfig(DeveloperRepository developerRepository, DeveloperIdGenerator idGenerator, RefreshTokenRepository refreshTokenRepository, AccessTokenStorage accessTokenStorage, UserRepository userRepository, UserIdGenerator userIdGenerator, PasswordGenerator passwordGenerator, AccessTokenIdGenerator accessTokenIdGenerator, RefreshTokenIdGenerator refreshTokenIdGenerator, TokenSaltGenerator tokenSaltGenerator, String gitGubClientSecret, String gitGubClientId) {
        this.refreshTokenIdGenerator = refreshTokenIdGenerator;
        this.tokenSaltGenerator = tokenSaltGenerator;
        this.developerRepository = developerRepository;
        this.accessTokenIdGenerator = accessTokenIdGenerator;
        this.idGenerator = idGenerator;
        this.gitGubClientSecret = gitGubClientSecret;
        this.gitGubClientId = gitGubClientId;
        this.authorizationByTokenUseCase = new AuthorizationByTokenUseCase(refreshTokenRepository, accessTokenStorage, userRepository);
        this.refreshTokenRepository = refreshTokenRepository;
        this.accessTokenStorage = accessTokenStorage;
        this.userRepository = userRepository;
        this.createRefreshTokenUseCase = new CreateRefreshTokenUseCase(refreshTokenIdGenerator, refreshTokenRepository, tokenSaltGenerator);
        this.createAccessTokenUseCase = new CreateAccessTokenUseCase(accessTokenIdGenerator, createRefreshTokenUseCase);
        this.userIdGenerator = userIdGenerator;
        this.passwordGenerator = passwordGenerator;
        this.emailAuthenticationUseCase = new EmailAuthenticationUseCase(this.userRepository, this.passwordGenerator, this.createRefreshTokenUseCase, this.createAccessTokenUseCase, this.refreshTokenRepository);

        this.secured = new Secured(this.developerRepository, this.idGenerator, this.authorizationByTokenUseCase);
        this.all = new All(
                this.gitGubClientId,
                this.developerRepository,
                this.refreshTokenRepository,
                this.accessTokenStorage,
                this.userRepository,
                this.createAccessTokenUseCase,
                this.userIdGenerator,
                this.passwordGenerator,
                this.emailAuthenticationUseCase,
                this.gitGubClientSecret
        );
    }

}