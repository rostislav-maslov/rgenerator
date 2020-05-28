package tech.maslov.rgenerator.client.config;

import com.rcore.adapter.domain.token.dto.AccessTokenDTO;
import com.rcore.adapter.domain.token.dto.RefreshTokenDTO;
import com.rcore.domain.file.port.FileRepository;
import com.rcore.domain.file.port.FileStorage;
import com.rcore.domain.picture.port.PictureIdGenerator;
import com.rcore.domain.picture.port.PictureRepository;
import com.rcore.domain.picture.port.PictureStorage;
import com.rcore.domain.role.port.RoleRepository;
import com.rcore.domain.token.port.*;
import com.rcore.domain.token.port.impl.TokenSaltGeneratorImpl;
import com.rcore.domain.token.usecase.AuthorizationByTokenUseCase;
import com.rcore.domain.user.port.PasswordGenerator;
import com.rcore.domain.user.port.UserIdGenerator;
import com.rcore.domain.user.port.UserRepository;
import com.rcore.domain.user.port.impl.PasswordGeneratorImpl;
import com.rcore.security.infrastructure.AuthTokenGenerator;
import com.rcore.security.infrastructure.jwt.converter.impl.JWTByAccessTokenGenerator;
import com.rcore.security.infrastructure.jwt.converter.impl.JWTByRefreshTokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.maslov.rgenerator.adapter.developer.DeveloperAdapter;
import tech.maslov.rgenerator.adapter.generator.GeneratorAdapter;
import tech.maslov.rgenerator.adapter.templateResult.TemplateResultAdapter;
import tech.maslov.rgenerator.domain.developer.config.DeveloperConfig;
import tech.maslov.rgenerator.domain.developer.port.DeveloperIdGenerator;
import tech.maslov.rgenerator.domain.developer.port.DeveloperRepository;
import tech.maslov.rgenerator.domain.generator.config.GeneratorConfig;
import tech.maslov.rgenerator.domain.generator.port.GeneratorIdGenerator;
import tech.maslov.rgenerator.domain.generator.port.GeneratorRepository;
import tech.maslov.rgenerator.domain.templateResult.config.TemplateResultConfig;
import tech.maslov.rgenerator.domain.templateResult.port.TemplateResultIdGenerator;
import tech.maslov.rgenerator.domain.templateResult.port.TemplateResultRepository;
import tech.maslov.rgenerator.domain.templateResult.service.ZipService;

@RequiredArgsConstructor
@Configuration
public class RGeneratorClientApplicationConfig {
    private final GeneratorRepository generatorRepository;
    private final GeneratorIdGenerator generatorIdGenerator;

    private final TemplateResultRepository templateResultRepository;
    private final TemplateResultIdGenerator templateResultIdGenerator;

    private final UserRepository userRepository;
    private final UserIdGenerator userIdGenerator;

    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenIdGenerator refreshTokenIdGenerator;
    private final TokenSaltGenerator tokenSaltGenerator = new TokenSaltGeneratorImpl();

    private final AccessTokenIdGenerator accessTokenIdGenerator;
    private final AccessTokenStorage accessTokenStorage;

    private final RoleRepository roleRepository;

    private final PictureRepository pictureRepository;
    private final PictureIdGenerator pictureIdGenerator;
    private final PictureStorage pictureStorage;

    private final FileRepository fileRepository;
    private final FileStorage fileStorage;

    private final DeveloperRepository developerRepository;
    private final DeveloperIdGenerator developerIdGenerator;
    private final PasswordGenerator passwordGenerator = new PasswordGeneratorImpl();

    @Bean
    public AuthTokenGenerator<AccessTokenDTO> accessTokenGenerator() {
        return new JWTByAccessTokenGenerator();
    }

    @Bean
    public AuthTokenGenerator<RefreshTokenDTO> refreshTokenGenerator() {
        return new JWTByRefreshTokenGenerator();
    }

    @Bean
    public GeneratorAdapter generatorAdapter() {
        AuthorizationByTokenUseCase authorizationByTokenUseCase = new AuthorizationByTokenUseCase(refreshTokenRepository, accessTokenStorage, userRepository);

        return new GeneratorAdapter(new GeneratorConfig(generatorRepository, generatorIdGenerator, authorizationByTokenUseCase, fileRepository, fileStorage, refreshTokenRepository, accessTokenStorage, userRepository, developerRepository));
    }

    @Bean
    public ZipService zipService() {
        return new ZipService(fileRepository, fileStorage);
    }

    @Bean
    public TemplateResultAdapter templateResultAdapter() {
        AuthorizationByTokenUseCase authorizationByTokenUseCase = new AuthorizationByTokenUseCase(refreshTokenRepository, accessTokenStorage, userRepository);

        return new TemplateResultAdapter(
                new TemplateResultConfig(templateResultRepository, generatorRepository, templateResultIdGenerator, authorizationByTokenUseCase, fileRepository, fileStorage)
        );
    }

    @Bean
    public DeveloperAdapter developerAdapter() {
        return new DeveloperAdapter(
                new DeveloperConfig(
                        developerRepository,
                        developerIdGenerator,
                        refreshTokenRepository,
                        accessTokenStorage,
                        userRepository,
                        userIdGenerator,
                        passwordGenerator,
                        accessTokenIdGenerator,
                        refreshTokenIdGenerator,
                        tokenSaltGenerator
                )
        );
    }

}
