package tech.maslov.rgenerator.adapter.developer;

import com.rcore.adapter.domain.token.dto.AccessTokenDTO;
import com.rcore.adapter.domain.token.dto.RefreshTokenDTO;
import com.rcore.adapter.domain.token.mapper.AccessTokenMapper;
import com.rcore.adapter.domain.token.mapper.RefreshTokenMapper;
import com.rcore.adapter.domain.user.dto.TokenPairDTO;
import com.rcore.adapter.domain.user.mapper.TokenPairMapper;
import com.rcore.domain.base.port.SearchRequest;
import com.rcore.domain.base.port.SearchResult;
import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.token.exception.RefreshTokenCreationException;
import com.rcore.domain.user.exception.UserAlreadyExistException;
import com.rcore.domain.user.exception.UserBlockedException;
import com.rcore.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import tech.maslov.rgenerator.adapter.developer.dto.DeveloperDTO;
import tech.maslov.rgenerator.adapter.developer.mapper.DeveloperMapper;
import tech.maslov.rgenerator.domain.developer.config.DeveloperConfig;
import tech.maslov.rgenerator.domain.developer.entity.DeveloperEntity;

import java.util.Optional;

@RequiredArgsConstructor
public class DeveloperRestAdapter {
    private final DeveloperConfig config;
    private final DeveloperMapper mapper = new DeveloperMapper();
    private final AccessTokenMapper accessTokenMapper = new AccessTokenMapper();
    private final RefreshTokenMapper refreshTokenMapper = new RefreshTokenMapper();
    private final TokenPairMapper tokenPairMapper = new TokenPairMapper(accessTokenMapper, refreshTokenMapper);

    public Optional<DeveloperDTO> findById(String id) throws AuthenticationException, AuthorizationException {
        return config.secured.viewUseCase()
                .findById(id)
                .map(mapper::map);
    }

    public TokenPairDTO login(String email, String password) throws AuthenticationException, UserNotFoundException, UserBlockedException {
        return tokenPairMapper.map(config.all.loginUseCase().authentication(email, password));
    }

    public DeveloperDTO currentDeveloper() throws AuthenticationException {
        return mapper.map(config.all.authorizationDevByTokenUseCase().currentDeveloper().get());
    }

    public DeveloperDTO me() throws AuthenticationException {
        return mapper.map(config.all.meUseCase().me());
    }

    public TokenPairDTO signUp(String login, String email, String password) throws UserAlreadyExistException, AuthenticationException, RefreshTokenCreationException, UserNotFoundException, UserBlockedException {
        return tokenPairMapper.map(config.all.signUpUseCase().signUp(login, email, password));
    }

}
